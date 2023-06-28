package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.product.Coupon;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, UUID> {
    List<OrderHistory> findByUserInfo(UserInfo userInfo);
    Page<OrderHistory> findByUserInfo(UserInfo userInfo, Pageable pageable);

    List<OrderHistory> findByCreatedAtBetween(Date startDate, Date endDate);

    @Query(value = "SELECT * FROM order_history ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<OrderHistory> getLastFiveOrders();

    //TODO; not working
    @Query(value = "select order_history.* from order_history" +
            " inner join user_info on order_history.user_id = user_info.uuid" +
            " where user_info.username like :keyword% or order_history.uuid like keyword%", nativeQuery = true)
    Page<OrderHistory> findOrderByKeyword(String keyword, Pageable pageable);

    Page<OrderHistory> findByUuidLike(UUID uuid, Pageable pageable);

    @Query(value = "SELECT * FROM order_history WHERE :filter = :val",
            countQuery = "SELECT COUNT(*) FROM order_history WHERE :filter = :val",
            nativeQuery = true)
    Page<OrderHistory> filter(@Param("filter") String filter, @Param("val") String val, Pageable pageable);


    Page<OrderHistory> findByOrderType(OrderType type, Pageable pageable);

    Page<OrderHistory> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    Page<OrderHistory> findByCoupon(Coupon coupon, Pageable pageable);

    Page<OrderHistory> findByUserInfoAndDeleted(UserInfo userInfo, Boolean b, Pageable pageable);
}
