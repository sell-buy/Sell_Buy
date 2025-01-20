package com.sell_buy.sell_buy.api.service;
public interface OrderService {
    void getSellOrder(long seller_id);
    void getBuyOrder(long buyer_id);
    void getOrderId(long order_id);
}