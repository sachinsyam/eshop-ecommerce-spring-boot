package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.order.OrderItems;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {

    OrderItems save(OrderItems item);

    List<OrderItems> findByOrder(OrderHistory orderHistory);
}
