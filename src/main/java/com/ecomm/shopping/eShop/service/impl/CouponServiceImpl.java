package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.user.Cart;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.CouponRepository;
import com.ecomm.shopping.eShop.service.CouponService;
import com.ecomm.shopping.eShop.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    UserInfoService userInfoService;

    @Override
    public List<Coupon> getAll() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon get(UUID uuid) {
        return couponRepository.findById(uuid).orElse(null);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public void delete(UUID uuid) {
        couponRepository.deleteById(uuid);
    }

    @Override
    public String redeem(String code) {
        Coupon coupon = couponRepository.findByCode(code);
        if (!coupon.isEnabled()) {
            System.out.println("Coupon is disabled.");
            return "Coupon is disabled.";
        }

        LocalDate currentDate = LocalDate.now();
        if (coupon.getExpiryDate().isBefore(currentDate)) {
            System.out.println("Coupon has expired.");
            return "Coupon has expired.";
        }

        if (coupon.getCount() <= 0) {
            System.out.println("Coupon quantity is 0.");
            return "Coupon quantity is 0.";
        }
        coupon.setCount(coupon.getCount() - 1);
        coupon = couponRepository.save(coupon);
        System.out.println("Coupon:"+coupon.getCode()+" deducted"+" Remaining Qty:"+coupon.getCount());


        return "redeemed";
    }

    @Override
    public Coupon findByCode(String coupon) {
        return couponRepository.findByCode(coupon);
    }

    @Override
    public Page<Coupon> findByNameLike(String keyword, Pageable pageable) {
        return couponRepository.findByNameLikeOrCodeLike("%"+keyword+"%","%"+keyword+"%", pageable);
    }

    @Override
    public Page<Coupon> findAllPaged(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

    @Override
    public void saveToUser(Coupon coupon) {
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());
        userInfo.setCoupon(coupon);
        userInfoService.save(userInfo);
        System.out.println("Coupon "+coupon.getCode()+" set to user "+userInfo.getUsername());
    }

    @Override
    public void deleteFromUser() {
        UserInfo userInfo = userInfoService.findByUsername(getCurrentUsername());
        userInfo.setCoupon(null);
        userInfoService.save(userInfo);
        System.out.println("Coupon deleted from user "+userInfo.getUsername());
    }

    //Fucked up coupon relationship. Should have made separate relationships with product and category!.
    //Code below is a temporary fix! TODO;
    @Override
    public List<Coupon> findCouponsApplicableForCart(List<Cart> cartItems) {
        List<Coupon> generalCoupons = this.findByCouponType(4);
        List<Coupon> productCoupons = this.findByCouponType(1);
        List<Coupon> categoryCoupons = this.findByCouponType(2);


        Set<Coupon> applicableCoupons = new HashSet<>(generalCoupons.stream()
                .filter(coupon -> !coupon.isExpired())
                .toList());

        for (Cart item : cartItems) {
            //not working
          //  List<Coupon> categoryCoupons = this.findByApplicableFor(uuid);

            //add coupons applicable to the product. temp sol.
            applicableCoupons.addAll(
                    productCoupons.stream()
                            .filter(coupon -> !coupon.isExpired() && coupon.getApplicableFor().equals(item.getVariant().getProductId().getUuid()))
                            .toList()
            );

         //   List<Coupon> productCoupons = this.findByApplicableFor(uuid);

            applicableCoupons.addAll(
                    categoryCoupons.stream()
                            .filter(coupon -> !coupon.isExpired() && coupon.getApplicableFor().equals(item.getVariant().getProductId().getCategory().getUuid()))
                            .toList()
            );
        }

        return new ArrayList<>(applicableCoupons);
    }
//inefficient code
//    public List<Coupon> findCouponsApplicableForCart(List<Cart> cartItems) {
//        List<Coupon> applicableCoupons = new ArrayList<>();
//        List<Coupon> generalCoupons = this.findByCouponType(4);
//        //adding general coupons
//        for(Coupon coupon : generalCoupons){
//            if(!coupon.isExpired()){
//                applicableCoupons.add(coupon);
//            }
//        }
//
//        for(Cart item : cartItems){
//            //adding category coupon
//            List<Coupon> categoryCoupons = this.findByApplicableFor(item.getVariant().getProductId().getCategory().getUuid());
//            for(Coupon coupon: categoryCoupons){
//                if(!coupon.isExpired() && !applicableCoupons.contains(coupon)){
//                    applicableCoupons.add(coupon);
//                }
//            }
//            List<Coupon> productCoupons = this.findByApplicableFor(item.getVariant().getProductId().getUuid());
//            for(Coupon coupon : productCoupons){
//                if(!coupon.isExpired() && !applicableCoupons.contains(coupon)){
//                    applicableCoupons.add(coupon);
//                }
//            }
//        }
//
//        return applicableCoupons;
//    }

    public List<Coupon> findByApplicableFor(UUID uuid){
       // return couponRepository.findByApplicableForAndEnabled(uuid, true);
        return couponRepository.findByApplicableFor(uuid);
    }

    public List<Coupon> findByCouponType(int type){
        return couponRepository.findByCouponTypeAndEnabled(type, true);
    }

    @Override
    public Coupon findById(UUID uuid) {

        return couponRepository.findById(uuid).orElse(null);
    }

    @Override
    public List<Coupon> findByApplicableForProduct(UUID uuid) {
        List<Coupon> productCoupons = this.findByCouponType(1);
        List<Coupon> applicableCoupons = new ArrayList<>();
        //add coupons applicable to the product. temp sol.
        applicableCoupons.addAll(
                productCoupons.stream()
                        .filter(coupon -> !coupon.isExpired() && coupon.getApplicableFor().equals(uuid))
                        .toList()
        );

        return applicableCoupons;
    }


    //getting current username
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

    }
}
