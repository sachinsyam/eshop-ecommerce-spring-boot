package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.dto.CouponValidityResponseDto;
import com.ecomm.shopping.eShop.entity.user.Cart;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CartService {
    List<Cart> findByUser(UserInfo userInfo);

    Cart save(Cart newCartItem);

    void delete(Cart cart);

    void deleteCartById(UUID uuid);

    Cart findCartByUuid(UUID uuid);

    boolean addToCart(String variantId, int qty);

    CouponValidityResponseDto checkCouponValidity();
}
