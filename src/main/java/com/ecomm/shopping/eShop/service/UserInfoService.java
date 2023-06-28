package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.user.AuditLog;
import com.ecomm.shopping.eShop.entity.user.Cart;
import com.ecomm.shopping.eShop.entity.user.UserAddress;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.AuditLogRepository;
import com.ecomm.shopping.eShop.repository.CartRepository;
import com.ecomm.shopping.eShop.repository.UserAddressRepository;
import com.ecomm.shopping.eShop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserAddressRepository userAddressRepository;
    @Autowired
    AuditLogRepository auditLogRepository;


    //all
    public List<UserInfo> loadAllUsers(){
        return  userInfoRepository.findAll();
    }

    public Page<UserInfo> loadAllUsers(Pageable pageable){
        return  userInfoRepository.findAll(pageable);
    }

    //search
//    public List<UserInfo> searchUsers(String keyword){
//        return  userInfoRepository.findByKeyword(keyword);
//    }
    //findById
    public UserInfo getUser(UUID id){
        return userInfoRepository.findById(id).orElse(null); //new way
     }
     //save user
    public void updateUser(UserInfo userInfo) { userInfoRepository.save(userInfo);  }

    public UserInfo save(UserInfo userInfo){
        return userInfoRepository.save(userInfo);
    }

//    public int updateUser2(UserInfo user){
//      return userInfoRepository.updateUsername(user.getUsername(),user.getId());
//    }
    //delete user
    public void delete(UUID id) {
        //delete cart items first
        List<Cart> cartItems = cartRepository.findByUserInfo(userInfoRepository.findById(id).orElse(null));
        for(Cart item : cartItems){
            cartRepository.delete(item);
        }
        //delete address
        List<UserAddress> userAddresses = userAddressRepository.findByUserInfo(userInfoRepository.findById(id).orElse(null));
        if (userAddresses.size()!=0) {
            for(UserAddress address : userAddresses){
                userAddressRepository.delete(address);
            }
        }

       // userAddressRepository.deleteByUserId(id);

        userInfoRepository.deleteById(id);
    }

    public Optional<UserInfo> searchUsers(String keyword) {
        return userInfoRepository.findByUsername(keyword);
    }

    public UserInfo findByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }

    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username).orElse(null);
    }

    public UserInfo findByPhone(String phone) {
        return userInfoRepository.findByPhone(phone);
    }

    public Page<UserInfo> search(String keyword, Pageable pageable) {
        return userInfoRepository.search(keyword, pageable);
    }

    public void deleteCoupon(UserInfo userInfo) {
        userInfo.setCoupon(null);
        userInfoRepository.save(userInfo);
    }

    public Page<AuditLog> getAudit(Pageable pageable) {
        return auditLogRepository.findAll(pageable);
    }
}
