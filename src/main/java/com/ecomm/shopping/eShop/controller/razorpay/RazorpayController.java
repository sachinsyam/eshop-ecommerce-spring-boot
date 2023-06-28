package com.ecomm.shopping.eShop.controller.razorpay;

import com.ecomm.shopping.eShop.dto.CartResponseDto;
import com.ecomm.shopping.eShop.dto.CouponValidityResponseDto;
import com.ecomm.shopping.eShop.dto.OrderRequestDto;
import com.ecomm.shopping.eShop.entity.order.OnlineOrderRef;
import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.order.OrderItems;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.product.Variant;
import com.ecomm.shopping.eShop.entity.user.Cart;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import com.ecomm.shopping.eShop.service.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/razorpay")
public class RazorpayController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    CartService cartService;
    @Autowired
    OrderHistoryService orderHistoryService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    VariantService variantService;
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    CouponService couponService;
    @Autowired
    OnlineOrderRefService onlineOrderRefService;


    private final RazorpayClient razorpayClient;


    public RazorpayController(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @PostMapping(value = "/pay", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CartResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto){
        System.out.println(orderRequestDto.toString());
        //get current user from spring session
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());

        List<Cart> cartItems = cartService.findByUser(userInfo);

        if(cartItems.size() == 0){
            System.out.println("Cart is empty. Cannot Checkout!");

            return new CartResponseDto();
        }

        //create a record in table
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrderStatus(OrderStatus.PAYMENT_PENDING);
        orderHistory.setOrderType(OrderType.ONLINE);
        orderHistory.setUserInfo(userInfo);
        UUID addressUuid = orderRequestDto.getAddress();
        orderHistory.setUserAddress(userAddressService.findById(addressUuid));
        orderHistory = orderHistoryService.save(orderHistory);

        //check if the coupon is valid. This method is fetching the coupon applied from the user_info table.
        CouponValidityResponseDto couponValidityResponseDto = cartService.checkCouponValidity();


        float gross = (float) couponValidityResponseDto.getCartTotal();
        float tax = gross / 100f *18f;
        float total = gross - tax;
        float offPrice = (float) couponValidityResponseDto.getPriceOff();

        orderHistory.setTotal(total);
        orderHistory.setOffPrice(offPrice);
        orderHistory.setTax(tax);
        orderHistory.setGross(gross);

        if(couponValidityResponseDto.isValid()){
            System.out.println("Coupon validation successful");

            orderRequestDto.setCoupon(couponValidityResponseDto.getCoupon());

            Coupon coupon = couponService.findByCode(couponValidityResponseDto.getCoupon());
            //reduce coupon count
            String res = couponService.redeem(coupon.getCode());

            if(res.equals("redeemed")){

                orderHistory.setCoupon(coupon);

                System.out.println("Coupon "+coupon.getCode()+" successfully redeemed for Order "+orderHistory.getUuid()+" by User:"+userInfo.getUsername());

                }else{
                    System.out.println("Valid Coupon Redemption Failed: "+coupon.toString());
                    couponValidityResponseDto.setPriceOff(0);
                    System.out.println(res);
                }

        }else{
            System.out.println("Validation failed");
        }


        //convert amount to paise for razorpay
        float amount = orderHistory.getGross() * 100f;

        orderRequestDto.setAmount(amount);
        orderRequestDto.setCurrency("INR");

        try {
            JSONObject options = new JSONObject();
            options.put("amount", orderRequestDto.getAmount());
            options.put("currency", orderRequestDto.getCurrency());
            options.put("receipt", orderHistory.getUuid());
            options.put("payment_capture", 1); // Auto-capture payment

            //add required values as notes
            JSONObject notes = new JSONObject();
            notes.put("coupon",couponValidityResponseDto.getCoupon());
            notes.put("off",couponValidityResponseDto.getPriceOff());
            options.put("notes",notes);

            //Call Razorpay api to create razorpay orderId.
            Order order = razorpayClient.orders.create(options);

            // Store the order ID for later reference
            String orderId = order.get("id");
            System.out.println("Razorpay OrderId Generated: "+orderId);

            //Move cart items to orderItems and link it with orderHistory.
            List<OrderItems> orderItemsList = new ArrayList<>();

            //add items from cart to orderItems
            for(Cart item : cartItems){
                //if requested qty > stock
                if(item.getQuantity() > item.getVariant().getStock()){
                    item.setQuantity(item.getVariant().getStock());
                }

                if(item.getQuantity() != 0){ //if itemQty == 0, after the previous if condition, it means that the product has gone out of stock.
                    OrderItems orderItem = new OrderItems();
                    orderItem.setVariant(item.getVariant());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setOrderPrice(item.getVariant().getSellingPrice());
                    orderItem.setOrderHistory(orderHistory);

                    orderItemsList.add(orderItem);
                }else{
                    System.out.println("Product gone out of stock while ordering!");
                }
            }
            orderHistory.setItems(orderItemsList);

            orderHistory = orderHistoryService.save(orderHistory);


            for(OrderItems item : orderItemsList){
                //reduce stock
                Variant variant = variantService.findById(item.getVariant().getUuid());
                variant.setStock(variant.getStock() - item.getQuantity());
                variantService.save(variant);
            }


            //record ids to order ref table
            OnlineOrderRef onlineOrderRef = new OnlineOrderRef();
            onlineOrderRef.setRazorPayOrderId(orderId);
            onlineOrderRef.setOrderHistory(orderHistory);
            onlineOrderRef = onlineOrderRefService.save(onlineOrderRef);

            //Delete items from cart
            for(Cart item : cartItems){
                cartService.delete(item);
            }

            CartResponseDto response = new CartResponseDto();
            response.setOrderId(orderId);
            response.setOrderUuid(orderHistory.getUuid().toString());

            return response;

        } catch (RazorpayException e) {
            // Handle any errors
            e.printStackTrace();
            CartResponseDto response = new CartResponseDto();
            response.setErrorDescription(e.getMessage());
            response.setErrorCode("3000"); // amount exceeds max amount allowed

            return response;
        }
    }


    @PostMapping(value = "/storePaymentErrorResponse", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> storePaymentErrorResponse(@RequestBody CartResponseDto cartResponseDto){
        System.out.println("Online Payment Failed:"+cartResponseDto.getErrorDescription());

        if (cartResponseDto.getOrderId() != null) {
            OnlineOrderRef onlineOrderRef = onlineOrderRefService.findByRazorpayOrderId(cartResponseDto.getOrderId());

            //Save all error references
            onlineOrderRef.setErrorCode(cartResponseDto.getErrorCode());
            onlineOrderRef.setErrorDesc(cartResponseDto.getErrorDescription());
            onlineOrderRef.setErrorSource(cartResponseDto.getErrorSource());
            onlineOrderRef.setErrorStep(cartResponseDto.getErrorStep());
            onlineOrderRef.setErrorPaymentId(cartResponseDto.getPaymentId());
            onlineOrderRef.setErrorReason(cartResponseDto.getErrorReason());
            onlineOrderRef.setStatus(false);

            onlineOrderRefService.save(onlineOrderRef);

            System.out.println("Error responses saved");

            // Return a success response with HTTP status 200
            return ResponseEntity.ok().body("Success");
        }
        return ResponseEntity.badRequest().body("No orderId in response");

    }

    //getting current username
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


}
