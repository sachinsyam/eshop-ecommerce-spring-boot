package com.ecomm.shopping.eShop.controller.cart;

import com.ecomm.shopping.eShop.dto.CouponValidityResponseDto;
import com.ecomm.shopping.eShop.dto.NewOrderDto;
import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.order.OrderItems;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.product.Variant;
import com.ecomm.shopping.eShop.entity.user.Cart;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import com.ecomm.shopping.eShop.repository.OrderHistoryRepository;
import com.ecomm.shopping.eShop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserInfoService userInfoService;
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

    @Value("${razorpay.apiKey}")
    private String apiKey;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;


    @GetMapping("/delete/{uuid}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String delete(@PathVariable UUID uuid){
        cartService.deleteCartById(uuid);
        return "redirect:/viewCart";
    }

    //move items from cart to order
    @PostMapping("/checkout")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Transactional
    public String checkout(@ModelAttribute NewOrderDto newOrderDto, Model model){
        //get current user from spring session
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());

        List<Cart> cartItems = cartService.findByUser(userInfo);

        if(!cartItems.isEmpty()){
            System.out.println("Processing COD order from user:"+userInfo.getUsername());
            List<OrderItems> orderItemsList = new ArrayList<>();

            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrderStatus(OrderStatus.PROCESSING);
            orderHistory.setOrderType(OrderType.COD);
            orderHistory.setUserInfo(userInfo);
            orderHistory.setUserAddress(userAddressService.findById(newOrderDto.getAddressId()));
            orderHistory = orderHistoryRepository.save(orderHistory);

            for (Cart item : cartItems) {

                if (item.getQuantity() > item.getVariant().getStock()) {
                    item.setQuantity(item.getVariant().getStock());
                }

                if (item.getQuantity() != 0) { //if itemQty == 0, after the previous if condition, it means that the product has gone out of stock.
                    OrderItems orderItem = new OrderItems();
                    orderItem.setVariant(item.getVariant());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setOrderPrice(item.getVariant().getSellingPrice());
                    orderItem.setOrderHistory(orderHistory);
                    orderItemsList.add(orderItem);
                }
            }


            CouponValidityResponseDto couponValidityResponseDto = cartService.checkCouponValidity();

            if(couponValidityResponseDto.isValid()){
                System.out.println("Coupon validation successful");

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



            float gross = (float) couponValidityResponseDto.getCartTotal();
            float tax = gross / 100f *18f;
            float total = gross - tax;
            float offPrice = (float) couponValidityResponseDto.getPriceOff();

            //Create Order

            orderHistory.setTotal(total);
            orderHistory.setTax(tax);
            orderHistory.setGross(gross);
            orderHistory.setOffPrice(offPrice);
            orderHistory.setItems(orderItemsList);

            orderHistory = orderHistoryService.save(orderHistory);

            for (OrderItems item : orderItemsList) {
                //reduce stock
                Variant variant = variantService.findById(item.getVariant().getUuid());
                variant.setStock(variant.getStock() - item.getQuantity());
                System.out.println(variant.getProductId().getName()+" "+variant.getName()+" stock updated to:"+variant.getStock());
                variantService.save(variant);
            }

            //Delete items from cart
            for (Cart item : cartItems) {
                cartService.delete(item);
                System.out.println("Cart Cleared for User:" + userInfo.getUsername());
            }
            couponService.deleteFromUser();


            return "redirect:/orderDetails?orderId=" + orderHistory.getUuid() + "&newOrderFlag=true";

        }else{
            //fetch order from orderHistory and convert to COD.
            System.out.println("Converting Order to COD");

            OrderHistory order = orderHistoryService.findById(UUID.fromString(newOrderDto.getGeneratedOrderUuid()));

            order.setOrderType(OrderType.COD);

            order.setOrderStatus(OrderStatus.PROCESSING);

            System.out.println("Order Converted to COD");

            return "redirect:/orderDetails?orderId=" + order.getUuid() + "&newOrderFlag=true";

        }
    }

    @PostMapping("/redeemCoupon")
    public String redeemCoupon(@ModelAttribute NewOrderDto newOrderDto){
        if(newOrderDto.getCoupon() == null){
            System.out.println("coupon is null" );
        }else{
            Coupon coupon = couponService.findByCode(newOrderDto.getCoupon());
            if(coupon == null){
                System.out.println("Coupon not found");
                return "redirect:/viewCart?invalidCoupon=true";
            } else if (!coupon.isEnabled()) {
                System.out.println("Coupon is disabled");
            } else if (coupon.getCount() == 0) {
                System.out.println("Coupon is exhausted");
            } else if (LocalDate.now().isAfter(coupon.getExpiryDate())) {
                System.out.println("Coupon Expired");
            } else{
                System.out.println("Coupon is valid, Proceed to Redemption");
                couponService.saveToUser(coupon);
            }

            return "redirect:/viewCart?couponCode=" + coupon.getCode();

        }
        return "redirect:/viewCart?invalidCoupon=true";
    }


    //getting current username
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
