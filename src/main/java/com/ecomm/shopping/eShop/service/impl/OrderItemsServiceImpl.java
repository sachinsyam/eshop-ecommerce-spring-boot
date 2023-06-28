package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.order.OrderItems;
import com.ecomm.shopping.eShop.repository.OrderItemsRepository;
import com.ecomm.shopping.eShop.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemsServiceImpl implements OrderItemService {
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Override
    public OrderItems save(OrderItems item) {
        return orderItemsRepository.save(item);
    }

    @Override
    public List<OrderItems> findByOrder(OrderHistory orderHistory) {
        return orderItemsRepository.findByOrderHistory(orderHistory);
    }
}
