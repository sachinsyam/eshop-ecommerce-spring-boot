package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.user.UserAddress;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.UserAddressRepository;
import com.ecomm.shopping.eShop.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Override
    public UserAddress save(UserAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }

    @Override
    public void deleteByUser() {

        //userAddressRepository.deleteByUser
    }

    @Override
    public List<UserAddress> findByUser(UserInfo user) {
        return userAddressRepository.findByUserInfo(user);
    }

    @Override
    public void deleteById(UUID uuid) {
        userAddressRepository.deleteById(uuid);
    }

    @Override
    public UserAddress findById(UUID addressUUID) {
        return userAddressRepository.findById(addressUUID).orElse(null);
    }

    @Override
    public void disableAddress(UUID uuid) {
        UserAddress userAddress = findById(uuid);
        userAddress.setEnabled(false);
        this.save(userAddress);
    }

    @Override
    public List<UserAddress> findByUserInfoAndEnabled(UserInfo userInfo, boolean b) {
        return userAddressRepository.findByUserInfoAndEnabled(userInfo, b);
    }
}
