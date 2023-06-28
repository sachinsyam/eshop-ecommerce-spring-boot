package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.Wallet;
import com.ecomm.shopping.eShop.entity.user.WalletHistory;
import com.ecomm.shopping.eShop.enums.WalletTransactionType;
import com.ecomm.shopping.eShop.repository.WalletRepository;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.ecomm.shopping.eShop.service.WalletHistoryService;
import com.ecomm.shopping.eShop.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    WalletHistoryService walletHistoryService;


    @Override
    public Wallet credit(Float amount) {
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());
        Wallet wallet = userInfo.getWallet();
        if(wallet == null){
            System.out.println("No wallet found for user, creating a new wallet with 0 bal");
            wallet = new Wallet();
            wallet.setUserInfo(userInfo);
            wallet.setBalance(0F);
        }
        wallet.setBalance(wallet.getBalance() + amount);

        WalletHistory walletHistory = new WalletHistory();
        walletHistory.setAmount(amount);
        walletHistory.setUserInfo(userInfo);
        walletHistory.setType(WalletTransactionType.CREDITED);

        walletHistoryService.recordTransaction(walletHistory);

        wallet = walletRepository.save(wallet);
        
        return wallet;
    }



    @Override
    public Wallet findByUserInfo(UserInfo user) {
        return walletRepository.findByUserInfo(user);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }


    //getting current logged in username
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
