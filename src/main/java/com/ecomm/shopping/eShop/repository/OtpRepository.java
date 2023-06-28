package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.OTP;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OtpRepository extends JpaRepository<OTP, UUID> {
    OTP findByUserInfo(UserInfo user);
}
