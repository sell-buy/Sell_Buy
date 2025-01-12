package com.sell_buy.sell_buy.api.service;

import com.sell_buy.sell_buy.db.entity.Order;

public interface OrderService {
    void getSellOrder(long seller_id);
    void getBuyOrder(long buyer_id);
    void getOrderId(long order_id);
    void setOrderId(long order_id);
    Order registerOrder(Order order);
    Order changeOrderStatus(Order order);
    boolean checkOrderId(boolean order_id);
    Order cancelOrder(long order_id);
}