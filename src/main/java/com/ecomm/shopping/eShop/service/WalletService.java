package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.Wallet;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    Wallet credit(Float amount);

    Wallet findByUserInfo(UserInfo user);

    Wallet save(Wallet wallet);

}
