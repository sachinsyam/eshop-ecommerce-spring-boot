package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public interface OrderHistoryService {

    OrderHistory save(OrderHistory orderHistory);

    Page<OrderHistory> findAll(Pageable pageable);

    OrderHistory findById(UUID uuid);

    Page<OrderHistory> findByUserInfo(UserInfo userInfo, Pageable pageable);
    Page<OrderHistory> findByUserInfoAndDeleted(UserInfo userInfo, Boolean b, Pageable pageable);

    void generateInvoice(UUID uuid);

    List<OrderHistory> findOrdersByDate(Date startDate, Date endDate);

    List<OrderHistory> getLastFiveOrders();

    Page<OrderHistory> searchOrderByKeyword(String keyword, Pageable pageable);

     Page<OrderHistory> findByOrderType(OrderType orderType, Pageable pageable);
     Page<OrderHistory> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    Page<OrderHistory> findByIdLike(String keyword, Pageable pageable);

    Page<OrderHistory> findByCoupon(Coupon coupon, Pageable pageable);
}
