package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.WalletHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface WalletHistoryRepository extends JpaRepository<WalletHistory, UUID> {
    List<WalletHistory> findByUserInfo(UserInfo userInfo);
    Page<WalletHistory> findByUserInfo(UserInfo userInfo, Pageable pageable);

    Page<WalletHistory> findAll(Pageable pageable);
}
