package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.user.OTP;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface OtpService {

    void save(OTP otp);

    OTP findByUser(UserInfo user);

    void delete(OTP oldOtp);

    boolean verifyPhoneOtp(String otp, String phone);

    void sendPhoneOtp(String phoneNumber);
}
