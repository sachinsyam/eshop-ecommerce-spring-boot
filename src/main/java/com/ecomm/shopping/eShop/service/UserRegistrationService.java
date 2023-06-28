package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegistrationService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAddressService userAddressService;

    public String addUser(UserInfo userInfo){

        if(userInfo.getUsername().equals("anonymousUser")){
            return "username";
        }

        Optional<UserInfo> existingUser1 = userInfoRepository.findByUsername(userInfo.getUsername());

        if(existingUser1.isPresent()){

            return "username";
        }

        UserInfo existingUser2 = userInfoRepository.findByEmail(userInfo.getEmail());
        if(existingUser2 != null){

            return "email";
        }
        UserInfo existingUser3 = userInfoRepository.findByPhone(userInfo.getPhone());
        if(existingUser3 != null){

            return "phone";
        }



            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

            userInfo.setVerified(false);

            userInfo = userInfoRepository.save(userInfo);

            //create an empty address field
//            UserAddress userAddress = new UserAddress();
//
//            userAddress.setUserInfo(userInfo);

        //    userAddressService.save(userAddress);

            return "signupSuccess";


    }

}
