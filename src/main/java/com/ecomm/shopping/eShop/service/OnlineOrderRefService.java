package com.ecomm.shopping.eShop.service;

import com.ecomm.shopping.eShop.entity.order.OnlineOrderRef;
import org.springframework.stereotype.Service;

@Service
public interface OnlineOrderRefService {
    OnlineOrderRef save(OnlineOrderRef onlineOrderRef);

    OnlineOrderRef findByRazorpayOrderId(String orderId);
}
