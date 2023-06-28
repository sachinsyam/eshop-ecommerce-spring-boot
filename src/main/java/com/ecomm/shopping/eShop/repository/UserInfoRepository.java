package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo>
    findByUsername(String username);

    UserInfo findByEmail(String email);

    UserInfo findByPhone(String phone);

    @Query(value = "SELECT * FROM user_info WHERE username LIKE :keyword% OR phone LIKE :keyword% OR phone LIKE :keyword% OR first_name LIKE :keyword% OR last_name LIKE :keyword% OR uuid like :keyword%", nativeQuery = true)
    Page<UserInfo> search(String keyword, Pageable pageable);
}
