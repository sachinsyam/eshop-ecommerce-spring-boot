package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.config.UserInfoToUserDetailsConversion;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoProviderService implements UserDetailsService {
   @Autowired
   UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //Getting user info from db.
      Optional<UserInfo> userInfo =   userInfoRepository.findByUsername(username);
      //intercept and handle otp login
//      if(userInfo.get().isOTPRequired()){
//          String password = userInfo.get().getPassword();
//
//      }

//      if (!userInfo.get().isEnabled()){
//          System.out.println(username+" is blocked");
//          throw new UsernameNotFoundException(username+" is blocked");
//      }else{
          //Converting user info to UserDetails so that it can be returned.
          return userInfo.map(UserInfoToUserDetailsConversion::new)
                  .orElseThrow(()-> new UsernameNotFoundException("user not found:"+username));
//      }

    }
}
