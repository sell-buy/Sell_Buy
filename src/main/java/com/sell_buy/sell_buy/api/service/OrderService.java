package com.sell_buy.sell_buy.api.service;

import com.sell_buy.sell_buy.db.entity.Order;

public interface OrderService {
    void getSellOrder(long sellerId);

    void getBuyOrder(long buyerId);

    void getOrderId(long orderId);

    void setOrderId(long orderId);

    Order registerOrder(Order order);

    void deleteOrder(long orderId);
}