package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.user.UserAddress;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {


    List<UserAddress> findByUserInfo(UserInfo user);

    List<UserAddress> findByUserInfoAndEnabled(UserInfo userInfo, boolean b);


//    @Query(value = "DELETE from user_address WHERE user_id = :id", nativeQuery = true)
//    void deleteByUserId(@Param("id")UUID id);


}
