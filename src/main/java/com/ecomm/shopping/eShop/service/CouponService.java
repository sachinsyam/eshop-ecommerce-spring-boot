package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.user.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CouponService {
    List<Coupon> getAll();

    Coupon get(UUID uuid);

    Coupon save(Coupon coupon);

    void delete(UUID uuid);

    String redeem(String code);

    Coupon findByCode(String coupon);

    Page<Coupon> findByNameLike(String keyword, Pageable pageable);

    Page<Coupon> findAllPaged(Pageable pageable);

    void saveToUser(Coupon coupon);

    void deleteFromUser();

    List<Coupon> findCouponsApplicableForCart(List<Cart> cartItems);
     List<Coupon> findByApplicableFor(UUID uuid);
     List<Coupon> findByCouponType(int type);

    Coupon findById(UUID uuid);

    List<Coupon> findByApplicableForProduct(UUID uuid);
}
