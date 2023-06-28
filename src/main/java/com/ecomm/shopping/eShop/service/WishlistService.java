package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Variant;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.Wishlist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WishlistService {

    List<Wishlist> findByUser(UserInfo user);

    boolean isWishlisted(Variant variant, UserInfo user);

    boolean save(Wishlist wishlist, UserInfo user, Variant variant);

    void delete(UUID uuid);

    Wishlist findById(UUID uuid);
}
