package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.WalletHistory;
import com.ecomm.shopping.eShop.enums.WalletTransactionType;
import com.ecomm.shopping.eShop.repository.WalletHistoryRepository;
import com.ecomm.shopping.eShop.service.WalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletHistoryServiceImpl implements WalletHistoryService {
    @Autowired
    WalletHistoryRepository walletHistoryRepository;

    @Override
    public WalletHistory recordTransaction(WalletHistory walletHistory) {
        return walletHistoryRepository.save(walletHistory);
    }

    @Override
    public List<WalletHistory> findTransactionsByUser(UserInfo userInfo) {
        return walletHistoryRepository.findByUserInfo(userInfo);
    }

    @Override
    public WalletHistory transfer(WalletTransactionType walletTransactionType, UserInfo userInfo, float amount) {
        WalletHistory walletHistory = new WalletHistory();
        walletHistory.setAmount(amount);
        walletHistory.setUserInfo(userInfo);
        walletHistory.setType(walletTransactionType);
        return walletHistoryRepository.save(walletHistory);
    }

    @Override
    public Page<WalletHistory> findByUserSortedByDate(UserInfo userInfo) {
        Pageable pageable = PageRequest.of(1, 100, Sort.by(Sort.Direction.fromString("ASC"), "createdAt"));
        return walletHistoryRepository.findAll(pageable);
    }

    @Override
    public Page<WalletHistory> findByUserInfo(UserInfo userInfo, Pageable pageable) {
        return walletHistoryRepository.findByUserInfo(userInfo, pageable);
    }

    @Override
    public Page<WalletHistory> findByUserInfoPaged(UserInfo user, Pageable pageable) {

        return walletHistoryRepository.findByUserInfo(user, pageable);
    }
}
