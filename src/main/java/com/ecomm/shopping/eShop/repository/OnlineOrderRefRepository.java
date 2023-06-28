package com.ecomm.shopping.eShop.repository;

import com.ecomm.shopping.eShop.entity.order.OnlineOrderRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OnlineOrderRefRepository extends JpaRepository<OnlineOrderRef, UUID> {
    OnlineOrderRef findByRazorPayOrderId(String orderId);
    //razorPayOrderId
}
