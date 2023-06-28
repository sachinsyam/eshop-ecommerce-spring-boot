package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.user.UserAddress;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserAddressService {

    UserAddress save(UserAddress userAddress);

    void deleteByUser();

    List<UserAddress> findByUser(UserInfo user);

    void deleteById(UUID uuid);

    UserAddress findById(UUID addressUUID);

    void disableAddress(UUID uuid);

    List<UserAddress> findByUserInfoAndEnabled(UserInfo userInfo, boolean b);
}
