package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.dto.CouponValidityResponseDto;
import com.ecomm.shopping.eShop.entity.product.Category;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.entity.product.Variant;
import com.ecomm.shopping.eShop.entity.user.Cart;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.CartRepository;
import com.ecomm.shopping.eShop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    VariantService variantService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Override
    public List < Cart > findByUser(UserInfo userInfo) {
        return cartRepository.findByUserInfo(userInfo);
    }

    @Override
    public Cart save(Cart newCartItem) {
        return cartRepository.save(newCartItem);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public void deleteCartById(UUID uuid) {
        cartRepository.deleteById(uuid);
    }

    @Override
    public Cart findCartByUuid(UUID uuid) {
        return cartRepository.findById(uuid).orElse(null);
    }

    @Override
    public boolean addToCart(String variantId, int qty) {
        boolean addedToCart = false;

        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());

        //Get Variant using id;
        Variant variant = variantService.findById(UUID.fromString(variantId));

        //fetch existing cart if exists or create a new cart for the user
        List < Cart > cart = findByUser(userInfo);

        if (variant.getStock() == 0) {
            return false;
        }

        Cart newCartItem = new Cart();

        if (cart.size() == 0) {

            newCartItem.setVariant(variant);
            newCartItem.setQuantity(Math.min(qty, 5));
            newCartItem.setUserInfo(userInfo);
            newCartItem = save(newCartItem);
            addedToCart = true;
        } else {
            //check if the item already exists in the cart
            boolean itemExists = false;
            for (Cart existingCartItem: cart) {
                if (existingCartItem.getVariant().equals(variant)) {
                    newCartItem = existingCartItem;
                    newCartItem.setQuantity(newCartItem.getQuantity() + qty);
                    if (newCartItem.getQuantity() > 5) {
                        newCartItem.setQuantity(5);
                    }
                    newCartItem = save(newCartItem);
                    itemExists = true;
                    break;
                }
            }
            if (!itemExists) {
                newCartItem.setVariant(variant);
                newCartItem.setQuantity(qty);
                newCartItem.setUserInfo(userInfo);
                newCartItem = save(newCartItem);
            }
            addedToCart = true;
        }
        return true;
    }

    @Override
    public CouponValidityResponseDto checkCouponValidity() {

        CouponValidityResponseDto couponValidityResponseDto = new CouponValidityResponseDto();

        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());
        Coupon coupon = userInfo.getCoupon();

        //find cart total
        double cartTotal = userInfo.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getVariant().getSellingPrice() * cartItem.getQuantity())
                .sum();
        //mrp
        double cartMrp = userInfo.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getVariant().getPrice() * cartItem.getQuantity())
                .sum();

        couponValidityResponseDto.setCartTotal(cartTotal);

        if(coupon == null){
            couponValidityResponseDto.setValid(false);

            return couponValidityResponseDto;
        }


        if (!coupon.isExpired() && coupon.isEnabled()) {

            switch (coupon.getCouponType()) {
                case 1 -> {
                    System.out.println("Product Coupon");
                    boolean productFoundInCart = false;
                    Float productPrice = 0F;
                    Product couponProduct = productService.getProduct(coupon.getApplicableFor());
                    if (couponProduct == null) {
                        System.out.println("Product in coupon not found");
                    } else {
                        for (Cart item : userInfo.getCartItems()) {
                            if (item.getVariant().getProductId().equals(couponProduct)) {
                                System.out.println("Product in coupon found in cart");
                                productFoundInCart = true;
                                productPrice = item.getVariant().getSellingPrice();
                                break;
                            }
                        }
                        if (productFoundInCart) {
                            float priceOff = productPrice / 100 * coupon.getOffPercentage();

                            priceOff = priceOff > coupon.getMaxOff() ? coupon.getMaxOff() : priceOff;

                            couponValidityResponseDto.setValid(true);
                            couponValidityResponseDto.setPriceOff(Math.round(priceOff));
                            couponValidityResponseDto.setCoupon(coupon.getCode());
                            couponValidityResponseDto.setCartTotal(Math.round(cartTotal - priceOff));

                            return couponValidityResponseDto;
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Category Coupon");
                    boolean categoryFound = false;
                    float offerPrice = 0F;
                    Category category = categoryService.getCategory(coupon.getApplicableFor());
                    if (category == null) {
                        System.out.println("Category Not Found");
                    } else {
                        for (Cart item : userInfo.getCartItems()) {
                            if (item.getVariant().getProductId().getCategory().equals(category)) {
                                System.out.println("Category in coupon found in cart item");
                                categoryFound = true;
                                offerPrice += item.getVariant().getSellingPrice();
                            }
                        }
                        if (categoryFound) {
                            float priceOff = offerPrice / 100 * coupon.getOffPercentage();
                            priceOff = priceOff > coupon.getMaxOff() ? coupon.getMaxOff() : priceOff;

                            couponValidityResponseDto.setValid(true);
                            couponValidityResponseDto.setPriceOff(Math.round(priceOff));
                            couponValidityResponseDto.setCoupon(coupon.getCode());
                            couponValidityResponseDto.setCartTotal(cartTotal - priceOff);


                            return couponValidityResponseDto;

                        } else {
                            System.out.println("No items in cart belong to the coupon category");
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Brand Coupon not enabled");
                    couponValidityResponseDto.setValid(false);
                    return couponValidityResponseDto;
                }
                case 4 -> {
                    System.out.println("General Coupon");
                    float priceOff = (float) (cartTotal / 100F * coupon.getOffPercentage());
                    priceOff = priceOff > coupon.getMaxOff() ? coupon.getMaxOff() : priceOff;

                    couponValidityResponseDto.setValid(true);
                    couponValidityResponseDto.setPriceOff(Math.round(priceOff));
                    couponValidityResponseDto.setCoupon(coupon.getCode());
                    couponValidityResponseDto.setCartTotal(Math.round(cartTotal - priceOff));

                    return couponValidityResponseDto;
                }
                case 5 -> {
                    System.out.println("User Coupon not enabled");
                    couponValidityResponseDto.setValid(false);
                    return couponValidityResponseDto;
                }
                default -> {
                    System.out.println("Unknown Coupon Type");
                    couponValidityResponseDto.setValid(false);
                    return couponValidityResponseDto;
                }
            }
        }
        System.out.println("Coupon is not valid");
        couponValidityResponseDto.setValid(false);
        return couponValidityResponseDto;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}