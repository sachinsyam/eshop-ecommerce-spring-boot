package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.WalletHistory;
import com.ecomm.shopping.eShop.enums.WalletTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletHistoryService {
    WalletHistory recordTransaction(WalletHistory walletHistory);

    List<WalletHistory> findTransactionsByUser(UserInfo userInfo);

    WalletHistory transfer(WalletTransactionType walletTransactionType, UserInfo userInfo, float amount);

    Page<WalletHistory> findByUserSortedByDate(UserInfo userInfo);

    Page<WalletHistory> findByUserInfo(UserInfo userInfo, Pageable pageable);

    Page<WalletHistory> findByUserInfoPaged(UserInfo user, Pageable pageable);
}
