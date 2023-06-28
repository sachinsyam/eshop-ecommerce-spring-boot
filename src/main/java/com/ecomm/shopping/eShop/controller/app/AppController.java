/*
 * Created by Sachin
 */
package com.ecomm.shopping.eShop.controller.app;

import com.ecomm.shopping.eShop.dto.CartResponseDto;
import com.ecomm.shopping.eShop.dto.CouponValidityResponseDto;
import com.ecomm.shopping.eShop.dto.CustomerOrderDto;
import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.order.OrderItems;
import com.ecomm.shopping.eShop.entity.product.*;
import com.ecomm.shopping.eShop.entity.user.*;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import com.ecomm.shopping.eShop.repository.AuditLogRepository;
import com.ecomm.shopping.eShop.service.*;
import com.ecomm.shopping.eShop.worker.SaveAnonUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AppController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    OrderHistoryService orderHistoryService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    BannerService bannerService;
    @Autowired
    WishlistService wishlistService;
    @Autowired
    OtpService otpService;
    @Autowired
    WalletService walletService;
    @Autowired
    CouponService couponService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;
    @Autowired
    VariantService variantService;
    @Autowired
    CartService cartService;
    @Autowired
    ProductReviewService productReviewService;
    @Autowired
    WalletHistoryService walletHistoryService;

    private String currentUser;
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    SaveAnonUserDetails saveAnonUserDetails;

    //getting current logged in username
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


/*
INTEGRATION OF SHOP
*/


    //shop homepage
    @GetMapping("/")
    public String home(@RequestParam(name = "search", required = false, defaultValue = "") String key,
                       Model model,
                       @PageableDefault(size = 8, sort = "name") Pageable pageable,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "8") int size,
                       @RequestParam(defaultValue = "name") String field,
                       @RequestParam(defaultValue = "ASC") String sort,
                       HttpServletRequest request) throws IOException {


        String currentUsername = String.valueOf(getCurrentUsername());
        UserInfo userInfo = userInfoService.findByUsername(currentUsername);


        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
            saveAnonUserDetails.save(request.getSession().getId(), request.getRemoteAddr());

        } else {
            model.addAttribute("loggedIn", true);
            //Check if the user is verified
            if (!isVerified(currentUsername)) {
                String phone = userInfo.getPhone();
                model.addAttribute("phone", phone);
                //send otp
                otpService.sendPhoneOtp(phone);

                return "shopView/verification";
            }
        }

        if (!currentUsername.equals("anonymousUser")) {
            if (userInfo.getSavedAddresses().size() != 0) {
                UserAddress userAddress = userInfo.getSavedAddresses().get(0);
                String flat = userAddress.getFlat();
                if (Objects.equals(flat, "") || Objects.equals(flat, null)) {
                    return "redirect:/profile";
                }
            } else {
                return "redirect:/profile";
            }
        }


        List<Banner> banners = bannerService.findAll();
        model.addAttribute("banners", banners);

        Map<String, Object> userPageValues = currentUserPageValues();

        //LISTING PRODUCTS
        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<Product> products;
        if (field.equals("price")) {
            List<Product> productList = productService.findAll();

            if (sort.equalsIgnoreCase("desc")) {

                productList = productList.stream()
                        .sorted(Comparator.comparing(Product::averagePrice).reversed())
                        .toList();
            } else {
                productList = productList.stream()
                        .sorted(Comparator.comparing(Product::averagePrice))
                        .collect(Collectors.toList());
            }


            int start = (int) (page * (long) size);
            int end = Math.min(start + size, productList.size());

            List<Product> pageContent = productList.subList(start, end);

            //Pageable pageable = PageRequest.of(page, size);
            products = new PageImpl<>(pageContent, pageable, productList.size());


        } else {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

            if (key == null || key.equals("")) {
                products = productService.findAll(pageable);
                model.addAttribute("products", products);
            } else {
                products = productService.findByNameLike(key, pageable);

                model.addAttribute("products", products);
            }
        }
        //     List<Product> filtered = products.stream().filter(Product::isEnabled).collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("search", "");


        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

        //pagination values
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);

        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, products.getTotalPages() - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", products.getTotalElements() == 0);

        if ( key.equals("")){
            return "shopView/index";
        }else{
            return "shopView/product.html";
        }

    }


    //shop list products
    @GetMapping("/product")
    public String product(@RequestParam(name = "search", required = false) String key,
                          Model model,
                          @PageableDefault(size = 8, sort = "name") Pageable pageable,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "8") int size,
                          @RequestParam(defaultValue = "name") String field,
                          @RequestParam(defaultValue = "ASC") String sort
    ) {

        String currentUsername = String.valueOf(getCurrentUsername());

        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
            //Check if the user is verified
            if (!isVerified(currentUsername)) {
                String phone = userInfoService.findByUsername(currentUsername).getPhone();
                model.addAttribute("phone", phone);
                //send otp
                otpService.sendPhoneOtp(phone);

                return "shopView/verification";
            }
        }


        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<Product> products;
        if (field.equals("price")) {
            List<Product> productList = productService.findAllEnabled();

            if (sort.equalsIgnoreCase("desc")) {

                productList = productList.stream()
                        .sorted(Comparator.comparing(Product::averagePrice).reversed())
                        .toList();
            } else {
                productList = productList.stream()
                        .sorted(Comparator.comparing(Product::averagePrice))
                        .collect(Collectors.toList());
            }


            int start = (int) (page * (long) size);
            int end = Math.min(start + size, productList.size());

            List<Product> pageContent = productList.subList(start, end);

            //Pageable pageable = PageRequest.of(page, size);
            products = new PageImpl<>(pageContent, pageable, productList.size());


        } else {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

            if (key == null) {
                products = productService.findAll(pageable);
                model.addAttribute("products", products);
            } else {
                products = productService.findByNameLike(key, pageable);

                model.addAttribute("products", products);
            }
        }
        //     List<Product> filtered = products.stream().filter(Product::isEnabled).collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("search", "");

        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

        //hide pagination links
        model.addAttribute("hidePagination", products.getTotalPages() == 1);

        //pagination values
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);

        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, products.getTotalPages() - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", products.getTotalElements() == 0);

        return "shopView/product.html";
    }


    //on clicking a product from shop
    @GetMapping("/productDetail")
    public String productDetails(@RequestParam(value = "productUuid", required = false) String productUuid,
                                 @RequestParam(value = "variantUuid", required = false) String variantUuid,
                                 @RequestParam(value = "addedToCart", required = false, defaultValue = "false") boolean addedToCart,
                                 @RequestParam(value = "addedToWishlist", required = false, defaultValue = "false") boolean addedToWishlist,
                                 @RequestParam(value = "reviewAdded", required = false, defaultValue = "false") boolean reviewAdded,
                                 @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                 Model model) {
        if(!Objects.equals(search, "")){
            return "redirect:/product?search="+search;
        }

        String currentUsername = String.valueOf(getCurrentUsername());



        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
            //Check if the user is verified
            if (!isVerified(currentUsername)) {
                String phone = userInfoService.findByUsername(currentUsername).getPhone();
                model.addAttribute("phone", phone);
                //send otp
                otpService.sendPhoneOtp(phone);

                return "shopView/verification";
            }
        }

        UserInfo userInfo = userInfoService.findByUsername(currentUsername);


        Product selectedProduct = new Product();
        Variant variant = new Variant();
        if (productUuid != null) {
            selectedProduct = productService.getProduct(UUID.fromString(productUuid));
        } else if (variantUuid != null) {
            variant = variantService.findById(UUID.fromString(variantUuid));
            selectedProduct = variant.getProductId();
        }

        if (selectedProduct.getUuid() == null) {
            //redirect to error page, write later!
            return "error";
        } else {
            if (variantUuid == null) {
                variant = selectedProduct.getVariants().get(0);
            }
            //check if variants are disabled.
            List<Variant> variants = selectedProduct.getVariants();
            variants.removeIf(item -> !item.isEnabled() || item.isDeleted());
            selectedProduct.setVariants(variants);

            //check if product is disabled
            if (!selectedProduct.isEnabled()) {
                return "redirect:/product";
            }

            //add reviews
            List<ProductReview> reviews = productReviewService.findByProduct(selectedProduct);
            //hide review form if user has already reviewed.

            //inefficient code
//            boolean reviewExist = false;
//            for(ProductReview review : reviews){
//                if(review.getUserInfo().equals(userInfo)){
//                    reviewExist = true;
//                }
//            }

            //check if the user has already reviewed.
            boolean reviewExist = reviews.stream()
                    .anyMatch(review -> review.getUserInfo().equals(userInfo));

            model.addAttribute("reviewExist", reviewExist);

            model.addAttribute("reviews", reviews);

            model.addAttribute("product", selectedProduct);

            model.addAttribute("selectedVariant", variant);

            model.addAttribute("wishlistStatus", wishlistService.isWishlisted(variant, userInfoService.findByUsername(getCurrentUsername())));

//            double productRating = selectedProduct.getRating();
//
//            model.addAttribute("productRating", productRating);

            //animations
            model.addAttribute("addedToCart", addedToCart);
            model.addAttribute("addedToWishlist", addedToWishlist);
            model.addAttribute("reviewAdded", reviewAdded);


            Map<String, Object> userPageValues = currentUserPageValues();

            model.addAttribute("cartItems", userPageValues.get("cartItems"));

            model.addAttribute("cartCount", userPageValues.get("cartCount"));

            model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

            model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

            List<Coupon> coupons =  couponService.findByApplicableForProduct(selectedProduct.getUuid());

            model.addAttribute("coupons", coupons);

            model.addAttribute("couponsAvailable", coupons.size()!=0);

        }

        return "shopView/product-detail";
    }

    @GetMapping("/productDetail/variant/{uuid}")
    public String viewVariant(@PathVariable UUID uuid, Model model) {

        String currentUsername = String.valueOf(getCurrentUsername());

        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
            //Check if the user is verified
            if (!isVerified(currentUsername)) {
                String phone = userInfoService.findByUsername(currentUsername).getPhone();
                model.addAttribute("phone", phone);
                //send otp
                otpService.sendPhoneOtp(phone);

                return "shopView/verification";
            }
        }

        Variant variant = variantService.findById(uuid);
        Product selectedProduct = variant.getProductId();

        //check if product is disabled
        if (!selectedProduct.isEnabled()) {
            return "redirect:/product";
        }

        model.addAttribute("product", selectedProduct);

        model.addAttribute("selectedVariant", variant);

        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

        model.addAttribute("wishlistStatus", wishlistService.isWishlisted(variant, userInfoService.findByUsername(getCurrentUsername())));

        return "shopView/product-detail";
    }

    @PostMapping("/productVariantDisplay")
    public String productVariantDisplay(@ModelAttribute Variant variant, Model model) {

        String currentUsername = String.valueOf(getCurrentUsername());
        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }


        if (variant.getUuid() != null) {
            Product product = productService.getProduct(variant.getProductId().getUuid());

            //check if product is disabled
            if (!product.isEnabled()) {
                return "redirect:/product";
            }

            model.addAttribute("product", product);
            model.addAttribute("selectedVariant", variant);
        }
        return "shopView/product-detail";
    }


    @GetMapping("changeAddress/{uuid}")
    public String changeAddress(@PathVariable(name = "uuid") UUID uuid) {
        return "redirect:/viewCart?addressUUID=" + uuid;
    }


    @GetMapping("viewCart")
    public String viewCart(Model model,
                           @RequestParam(required = false) UUID addressUUID,
                           @RequestParam(required = false) String couponCode,
                           @RequestParam(required = false, defaultValue = "false") boolean orderFailed,
                           @RequestParam(required = false, defaultValue = "false") boolean invalidCoupon,
                           @RequestParam(required = false, defaultValue = "false") boolean expired) {
        String currentUsername = String.valueOf(getCurrentUsername());
        if (currentUsername.equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());

        if(couponCode==null || couponCode == ""){
            if(userInfo.getCoupon() != null){
                userInfoService.deleteCoupon(userInfo);
            }
        }else{
            Coupon coupon = couponService.findByCode(couponCode);
            userInfo.setCoupon(coupon);
            userInfo = userInfoService.save(userInfo);
        }


        long count = userInfo.getSavedAddresses()
                .stream()
                .filter(a -> a.isEnabled())
                .count();

        if (count == 0) {
            System.out.println("No addresses found for user, redirecting to profile");
           return "redirect:/profile?addAddress=true";
        }


        List<Cart> cartItems = cartService.findByUser(userInfo);

        //remove items if they are out of stock or disabled
        Iterator<Cart> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            Cart item = iterator.next();
            if (item.getVariant().getStock() == 0 || !item.getVariant().getProductId().isEnabled()) {
                System.out.println("Removing " + item.getVariant().getProductId().getName() + "from cart");
                iterator.remove();
            } else if (item.getQuantity() > item.getVariant().getStock()) {
                item.setQuantity(item.getVariant().getStock());
            }
        }

        //find applicable coupons for the cart
        List<Coupon> applicableCoupons = couponService.findCouponsApplicableForCart(cartItems);

        model.addAttribute("applicableCoupons", applicableCoupons);

        List<UserAddress> addressList = userAddressService.findByUserInfoAndEnabled(userInfo,true);

        model.addAttribute("addressList", addressList);

        model.addAttribute("nameOfUser", userInfo.getFirstName() + " " + userInfo.getLastName());


        if (cartItems.size() == 0) {
            model.addAttribute("cartEmpty", true);
        } else {
            model.addAttribute("cartEmpty", false);
        }

        model.addAttribute("orderFailed", orderFailed);

        model.addAttribute("invalidCoupon", invalidCoupon);

        model.addAttribute("couponCode", couponCode);
        model.addAttribute("couponExpired", expired);


        model.addAttribute("loggedIn", true);


        model.addAttribute("wishlistCount", userInfo.getWishlist().size());

        model.addAttribute("cartItems", cartItems);
        //find cart total
        double cartTotal = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getVariant().getSellingPrice() * cartItem.getQuantity())
                .sum();
        //mrp
        double cartMrp = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getVariant().getPrice() * cartItem.getQuantity())
                .sum();

        float total = (float) cartTotal;
        float tax = total / 100 * 18; //18%
        total -= tax;
        float gross = total + tax;


        model.addAttribute("cartTotal", Math.round(total));

        model.addAttribute("cartTax", Math.round(tax));

        model.addAttribute("cartSaved", Math.round(cartMrp - cartTotal));

        model.addAttribute("cartMrp", Math.round(cartMrp));

        List<UserAddress> addresses = userAddressService.findByUserInfoAndEnabled(userInfo, true);

        UserAddress defaultAddress = addresses
                .stream()
                .filter(UserAddress::isDefaultAddress) // Filter addresses with isDefaultAddress true
                .findFirst() // Find the first matching address
                .orElse(null);
        if(defaultAddress == null){
            defaultAddress = addresses.get(0);
        }


        if (addressUUID == null) {
            model.addAttribute("address", defaultAddress);

        } else {
            model.addAttribute("address", userAddressService.findById(addressUUID));
        }

        //coupon redemption logic
        if (couponCode != null && !expired) {
            System.out.println("Coupon: " + couponCode);
            Coupon coupon = couponService.findByCode(couponCode);
            if (coupon == null) {
                System.out.println("Invalid coupon");
            } else {

                if (coupon.isExpired()) {
                    return "redirect:/viewCart?expired=true&couponCode=" + couponCode;
                }

                CouponValidityResponseDto couponValidityResponseDto = cartService.checkCouponValidity();

                if(couponValidityResponseDto.isValid()){
                    System.out.println("Coupon is valid");

                     total = (float) couponValidityResponseDto.getCartTotal();
                     tax = total / 100 * 18; //18%
                     total -= tax;
                     gross = total + tax;

                    model.addAttribute("cartTax", Math.round(tax));
                    model.addAttribute("priceOff", Math.round(couponValidityResponseDto.getPriceOff()));
                    model.addAttribute("cartTotal", Math.round(total));
                    model.addAttribute("cartSaved", Math.round(cartMrp - cartTotal + couponValidityResponseDto.getPriceOff()));

                        model.addAttribute("couponApplied", true);
                        model.addAttribute("couponCode", couponCode);
                }else{
                        model.addAttribute("couponApplied", false);
                        model.addAttribute("couponCode", "");
                        System.out.println("Coupon is invalid");

                    model.addAttribute("priceOff", Math.round(couponValidityResponseDto.getPriceOff()));
                    model.addAttribute("cartTotal", Math.round(couponValidityResponseDto.getCartTotal()));
                    model.addAttribute("cartSaved", Math.round(cartMrp - cartTotal - couponValidityResponseDto.getPriceOff()));
                }


                //check coupon type
//                switch (coupon.getCouponType()) {
//                    case 1 -> {
//                        System.out.println("Product Coupon");
//                        boolean productFoundInCart = false;
//                        Float productPrice = 0F;
//                        Product couponProduct = productService.getProduct(coupon.getApplicableFor());
//                        if (couponProduct == null) {
//                            System.out.println("Product in coupon not found");
//                        } else {
//                            for (Cart item : cartItems) {
//                                if (item.getVariant().getProductId().equals(couponProduct)) {
//                                    System.out.println("Product in coupon found in cart");
//                                    productFoundInCart = true;
//                                    productPrice = item.getVariant().getSellingPrice();
//                                    break;
//                                }
//                            }
//                            if (productFoundInCart) {
//                                float priceOff = productPrice / 100 * coupon.getOffPercentage();
//
//                                priceOff = priceOff > coupon.getMaxOff() ? coupon.getMaxOff() : priceOff;
//
//                                model.addAttribute("couponApplied", true);
//                                model.addAttribute("couponCode", couponCode);
//                                model.addAttribute("priceOff", Math.round(priceOff));
//                                model.addAttribute("cartTotal", Math.round(cartTotal - priceOff));
//                                model.addAttribute("cartSaved", Math.round(cartMrp - cartTotal + priceOff));
//
//                            }
//                        }
//                    }
//                    case 2 -> {
//                        System.out.println("Category Coupon");
//                        boolean categoryFound = false;
//                        float offerPrice = 0F;
//                        Category category = categoryService.getCategory(coupon.getApplicableFor());
//                        if (category == null) {
//                            System.out.println("Category Not Found");
//                        } else {
//                            for (Cart item : cartItems) {
//                                if (item.getVariant().getProductId().getCategory().equals(category)) {
//                                    System.out.println("Category in coupon found in cart item");
//                                    categoryFound = true;
//                                    offerPrice += item.getVariant().getSellingPrice();
//                                }
//                            }
//                            if (categoryFound) {
//                                float priceOff = offerPrice / 100 * coupon.getOffPercentage();
//                                priceOff = priceOff > coupon.getMaxOff() ? coupon.getMaxOff() : priceOff;
//
//                                model.addAttribute("couponApplied", true);
//                                model.addAttribute("couponCode", couponCode);
//                                model.addAttribute("priceOff", Math.round(priceOff));
//                                model.addAttribute("cartTotal", Math.round(cartTotal - priceOff));
//                                model.addAttribute("cartSaved", Math.round(cartMrp - cartTotal + priceOff));
//
//                            } else {
//                                System.out.println("No items in cart belong to the coupon category");
//                            }
//                        }
//                    }
//                    case 3 -> System.out.println("Brand Coupon");
//                    case 4 -> {
//                        System.out.println("General Coupon");
//                        float priceOff = (float) (cartTotal / 100F * coupon.getOffPercentage());
//                        priceOff = priceOff > coupon.getMaxOff() ? coupon.getMaxOff() : priceOff;
//                        model.addAttribute("couponApplied", true);
//                        model.addAttribute("couponCode", couponCode);
//                        model.addAttribute("priceOff", Math.round(priceOff));
//                        model.addAttribute("cartTotal", Math.round(cartTotal - priceOff));
//                        model.addAttribute("cartSaved", Math.round(cartMrp - cartTotal + priceOff));
//                    }
//                    case 5 -> System.out.println("User Coupon");
//                    default -> System.out.println("Unknown Coupon Type");
//                }
            }


        }


        return "shopView/shoping-cart";
    }

    //display user profile page
    @GetMapping("/profile")
    public String viewProfile(@RequestParam(name="addAddress", defaultValue = "false", required = false)boolean addAddress,
                              Model model) {

        String currentUsername = String.valueOf(getCurrentUsername());
        if (currentUsername.equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }

        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());


        boolean noAddressFound = false;

        if (userInfo.getSavedAddresses().size() == 0 || Objects.equals(userInfo.getSavedAddresses().get(0).getFlat(), null) || Objects.equals(userInfo.getSavedAddresses().get(0).getFlat(), "")) {
            model.addAttribute("setupAddressWarning", true);
            noAddressFound = true;
            //create an empty address if it's null
//            if(userInfo.getSavedAddresses().get(0) == null){
//
//                UserAddress userAddress = new UserAddress();
//                userAddress.setUserInfo(userInfo);
//                userAddress = userAddressService.save(userAddress);
//                List<UserAddress> list = new ArrayList<>();
//                list.add(userAddress);
//                userInfo.setSavedAddresses(list);
//                userInfo = userInfoService.save(userInfo);
//            }
        }

        model.addAttribute("wishlistCount", userInfoService.findByUsername(currentUsername).getWishlist().size());

        model.addAttribute("user", userInfo);


        List<UserAddress> addresses;
        if (noAddressFound) {
            addresses = new ArrayList<>();
//            addresses.add(new UserAddress());
            System.out.println("No addresses found");
        } else {
            addresses = userAddressService.findByUser(userInfo);

        }
        model.addAttribute("addresses", addresses);

        Wallet wallet = walletService.findByUserInfo(userInfo);
        if (wallet == null) {
            wallet = walletService.credit(0F);
        }

        model.addAttribute("walletBalance", wallet.getBalance());
        model.addAttribute("walletUpdateTime", formatDate(wallet.getModifiedAt()));


        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

        //show add address error
        model.addAttribute("addAddress",addAddress);


        return "shopView/profile.html";
    }

    //display order history
    @GetMapping("/orders")
    public String orders(@RequestParam(required = false,defaultValue = "") String filter,
                         @RequestParam(required = false, defaultValue = "") String keyword,
                         @RequestParam(required = false, defaultValue = "desc") String sort,
                         @RequestParam(required = false, defaultValue = "createdAt") String field,
                         @RequestParam(required = false, defaultValue = "0") int page,
                         @RequestParam(required = false, defaultValue = "10") int size,
                         Model model) {
        String currentUsername = String.valueOf(getCurrentUsername());
        if (currentUsername.equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));


        Page<OrderHistory> userOrders = orderHistoryService.findByUserInfoAndDeleted(userInfoService.findByUsername(getCurrentUsername()), false, pageable);
        List<CustomerOrderDto> orders = new ArrayList<>();

        for (OrderHistory order : userOrders) {
            CustomerOrderDto orderItem = new CustomerOrderDto();

            orderItem.setOrderType(order.getOrderType().toString());

            orderItem.setOrderStatus(order.getOrderStatus().toString());

            orderItem.setTax(order.getTax());

            orderItem.setUuid(order.getUuid());

            orderItem.setTotal(order.getTotal());

            orderItem.setDate(formatDate(order.getCreatedAt()));

            //add to list
           orders.add(orderItem);
        }


        model.addAttribute("orders", orders);

        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));



        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userOrders.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, userOrders.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", userOrders.getTotalElements() == 0);


        return "shopView/orders";
    }

    //this api is called by razorpay after payment
    @PostMapping("/paymentGatewayResponse")
    public String orderDetailsPost(@RequestParam(name = "orderHistoryId") UUID orderHistoryId,
                                   @ModelAttribute CartResponseDto cartResponseDto) {

        System.out.println("Payment Successful for Order UUID:" + orderHistoryId);
        //update payment status
        OrderHistory order = orderHistoryService.findById(orderHistoryId);

        order.setOrderStatus(OrderStatus.PROCESSING);

        if (order.getOnlineOrderRef().getRazorPayOrderId().equals(cartResponseDto.getRazorpay_order_id())) {

            System.out.println("Razorpay OrderId matched!");

            //Updating Razorpay payment success response.
            order.getOnlineOrderRef().setPaymentId(cartResponseDto.getRazorpay_payment_id());
            order.getOnlineOrderRef().setSignature(cartResponseDto.getRazorpay_signature());

            System.out.println("Updated Payment Success Response Razorpay orderId:" + order.getOnlineOrderRef().getRazorPayOrderId());

            orderHistoryService.save(order);

        } else {
            System.out.println("Razorpay orderId not found!");
            return "redirect:/paymentError";
        }

        return "redirect:/orderDetails?orderId=" + orderHistoryId + "&newOrderFlag=true";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(@RequestParam(name = "orderId") UUID orderId,
                               @RequestParam(name = "newOrderFlag", required = false, defaultValue = "false") boolean newOrderFlag,
                               Model model) {

        String currentUsername = String.valueOf(getCurrentUsername());

        if (currentUsername.equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }


        OrderHistory orderHistory = orderHistoryService.findById(orderId);
        List<OrderItems> orderItems = orderItemService.findByOrder(orderHistory);

        model.addAttribute("orderDate", formatDate(orderHistory.getCreatedAt()));
        model.addAttribute("order", orderHistory);
        model.addAttribute("orderItems", orderItems);


        model.addAttribute("orderSuccessfulAnimation", newOrderFlag);
        model.addAttribute("onlinePayment", orderHistory.getOrderType().equals(OrderType.ONLINE));

        String paymentErrorDesc = orderHistory.getOnlineOrderRef() == null ? "Payment Error" : orderHistory.getOnlineOrderRef().getErrorDesc();

        model.addAttribute("paymentErrorDesc", paymentErrorDesc);

        model.addAttribute("paymentError", orderHistory.getOrderStatus().equals(OrderStatus.PAYMENT_PENDING));

        if (orderHistory.getCoupon() == null) {
            model.addAttribute("couponApplied", false);
        } else {
            model.addAttribute("couponApplied", true);
        }

        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

        return "shopView/orderDetails";
    }

    //cancel order from user side
    @GetMapping("/cancelOrder")
    @Transactional
    public String cancelOrderFromUser(@RequestParam(name = "orderId") UUID orderId) {

        String currentUsername = String.valueOf(getCurrentUsername());
        if (currentUsername.equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserInfo userInfo = userInfoService.findByUsername(currentUsername);
        OrderHistory order = orderHistoryService.findById(orderId);

        //confirm if the order belongs to the current user.
        if (order.getUserInfo().equals(userInfo)) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            order = orderHistoryService.save(order);

            //increase stock
            for (OrderItems item : order.getItems()) {
                Variant variant = variantService.findById(item.getVariant().getUuid());
                variant.setStock(variant.getStock() + item.getQuantity());
                variantService.save(variant);
            }

            return "redirect:/orderDetails?orderId=" + orderId;
        }

        return "error";

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/returnOrder")
    public String returnOrder(@RequestParam(name = "orderId") UUID orderId) {
        OrderHistory orderHistory = orderHistoryService.findById(orderId);
        orderHistory.setOrderStatus(OrderStatus.REFUNDED);
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());
        orderHistoryService.save(orderHistory);
        walletService.credit(orderHistory.getGross());

        return "redirect:/orderDetails?orderId=" + orderId;
    }

    @GetMapping("/wishlist")
    public String viewWishlist(Model model) {

        String currentUsername = String.valueOf(getCurrentUsername());
        if (currentUsername.equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }

        UserInfo user = userInfoService.findByUsername(currentUsername);
        List<Wishlist> wishlist = wishlistService.findByUser(user);

        model.addAttribute("wishlist", wishlist);

        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

        return "shopView/wishlist";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/addToWishlist")
    public String addToWishlist(@RequestParam("variantId") UUID variantId) {
        UserInfo user = userInfoService.findByUsername(getCurrentUsername());
        Variant variant = variantService.findById(variantId);
        Wishlist wishlist = new Wishlist();
        wishlist.setVariant(variant);
        wishlist.setUserInfo(user);
        if(wishlistService.save(wishlist, user, variant)){
            return "redirect:/productDetail?variantUuid=" + variantId + "&addedToWishlist=true";
        }
        return "redirect:/productDetail?variantUuid=" + variantId + "&addedToWishlist=false";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/wishlist/delete")
    public String deleteWishlist(@RequestParam("uuid") UUID uuid) {
        wishlistService.delete(uuid);
        return "redirect:/wishlist";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/wishlist/addToCart")
    @Transactional
    public String wishListToCart(@RequestParam("uuid") UUID uuid) {
        Wishlist wishlistItem = wishlistService.findById(uuid);
        cartService.addToCart(String.valueOf(wishlistItem.getVariant().getUuid()), 1);
        wishlistService.delete(wishlistItem.getUuid());
        return "redirect:/viewCart";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/walletHistory")
    public String walletHistory(@RequestParam(required = false,defaultValue = "") String filter,
                                @RequestParam(required = false, defaultValue = "") String keyword,
                                @RequestParam(required = false, defaultValue = "DESC") String sort,
                                @RequestParam(required = false, defaultValue = "createdAt") String field,
                                @RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "10") int size,
                                Model model) {
        String currentUsername = String.valueOf(getCurrentUsername());
        if (currentUsername.equals("anonymousUser")) {
            return "redirect:/login";
        }
        if (currentUsername.equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }


        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());
        Wallet wallet = userInfo.getWallet();
        if (wallet == null) {
            System.out.println("Wallet is null, Crediting 0 and debiting 0");
            wallet = walletService.credit(0F);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<WalletHistory> walletHistories = walletHistoryService.findByUserInfo(userInfo, pageable);



        model.addAttribute("transactions", walletHistories);
        model.addAttribute("walletBalance", wallet.getBalance());
        model.addAttribute("walletUpdateTime", formatDate(wallet.getModifiedAt()));




        model.addAttribute("transactionsEmpty", false);

        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));

        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", walletHistories.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, walletHistories.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", walletHistories.getTotalElements() == 0);





        return "shopView/walletHistory";
    }


    //Product Review
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/addReview")
    public String addReview(@ModelAttribute ProductReview productReview) {
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());

        //check if a review already exist for the product by the same user. TODO; disable multi review
        if(productReviewService.findByProductAndUserInfo(productReview.getProduct(), userInfo).size() != 0){
            System.out.println("A review already exist for the product from user");
            return "redirect:/productDetail?productUuid="+productReview.getProduct().getUuid();
        }

        //make sure rating is between 1-5
        int rating = productReview.getRating() > 5 ? 5 : Math.max(productReview.getRating(), 1);
        productReview.setRating(rating);
        productReview.setUserInfo(userInfo);

        productReviewService.save(productReview);
        System.out.println("Review Saved");
        return "redirect:/productDetail?reviewAdded=true&productUuid=" + productReview.getProduct().getUuid();
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/deleteCoupon")
    public String deleteCoupon(){
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());
        userInfo.setCoupon(null);
        userInfoService.save(userInfo);
        System.out.println("Coupon cleared from user");
        return "redirect:/viewCart";
    }

    @GetMapping("/contact")
    public String contact(Model model){

        Map<String, Object> userPageValues = currentUserPageValues();

        model.addAttribute("cartItems", userPageValues.get("cartItems"));

        model.addAttribute("cartCount", userPageValues.get("cartCount"));

        model.addAttribute("wishlistCount", userPageValues.get("wishlistCount"));

        model.addAttribute("cartTotal", userPageValues.get("cartTotal"));


        if (getCurrentUsername().equals("anonymousUser")) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }




        return "shopView/contact";
    }


    String formatDate(Date date) {
        //format date to readable format
        LocalDateTime dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(formatter);
    }

    Map<String, Object> currentUserPageValues() {
        Map<String, Object> userValues = new HashMap<>();
        String currentUsername = String.valueOf(getCurrentUsername());
        if (!currentUsername.equals("anonymousUser")) {
            UserInfo userInfo = userInfoService.findByUsername(currentUsername);
            userValues.put("cartItems", userInfo.getCartItems());
            userValues.put("wishlistCount", userInfo.getWishlist().size());
            userValues.put("cartCount", userInfo.getCartItems().size());

            double cartTotal = userInfo.getCartItems().stream()
                    .mapToDouble(cartItem -> cartItem.getVariant().getSellingPrice() * cartItem.getQuantity())
                    .sum();
            userValues.put("cartTotal", cartTotal);

        } else {
            userValues.put("cartItems", null);
            userValues.put("wishlistCount", null);
            userValues.put("cartCount", null);
            userValues.put("cartTotal", null);
        }
        return userValues;
    }

    private boolean isVerified(String username) {
//        UserInfo user = userInfoService.findByUsername(username);
//        if (user.getUuid() != null) {
//            return user.isVerified();
//        } else {
//            return true;
//        }
        //DISABLED VERIFICATION BECAUSE TWILIO IS SENDING OTP TO MY NUMBER
        return true;
    }

}
