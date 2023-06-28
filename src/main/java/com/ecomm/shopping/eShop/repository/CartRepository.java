package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.Cart;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findByUserInfo(UserInfo userInfo);
}
