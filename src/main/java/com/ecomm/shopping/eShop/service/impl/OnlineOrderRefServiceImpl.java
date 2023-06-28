package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.order.OnlineOrderRef;
import com.ecomm.shopping.eShop.repository.OnlineOrderRefRepository;
import com.ecomm.shopping.eShop.service.OnlineOrderRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineOrderRefServiceImpl implements OnlineOrderRefService {
    @Autowired
    OnlineOrderRefRepository onlineOrderRefRepository;

    @Override
    public OnlineOrderRef save(OnlineOrderRef onlineOrderRef) {
        return onlineOrderRefRepository.save(onlineOrderRef);
    }

    @Override
    public OnlineOrderRef findByRazorpayOrderId(String orderId) {
        return onlineOrderRefRepository.findByRazorPayOrderId(orderId);
    }
}
