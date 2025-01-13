package com.sell_buy.sell_buy.api.service;

import com.sell_buy.sell_buy.db.entity.Order;

import java.util.List;

public interface OrderService {
    void getSellOrder(long sellerId);

    void getBuyOrder(long buyerId);

    void getOrderId(long orderId);

    void setOrderId(long orderId);

    // 오더 등록
    Order registerOrder(Order order);

    // 오더 삭제
    void deleteOrder(long orderId);

    //오더가 존재하는지
    boolean hasExistOrder(Long orderId);

    // 모든 오더아이디값 가져오기
    List<Order> getAllOrderId();

    //모든 오더아이디값의 베송상태 확인
    void updateOrderStatus(long orderId);
}