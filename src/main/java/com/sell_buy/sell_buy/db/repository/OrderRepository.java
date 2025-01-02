package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {
    Order findOrderByOrderId(Long OrderId);  //상품정보로 조회 치환필요
    Order findByBuyerId(Long BuyerId);  //구매한 상품
    Order finbysellerId(Long sellerId); //판매상품
}
