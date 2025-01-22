package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Order findByOrderId(long orderId);

    void findByBuyerId(long BuyerId);  //구매한 상품

    void findBySellerId(long sellerId);
    
    Order findAllByOrderId(long orderId);

    List<Order> orderId(long orderId);

    boolean existsByProdId(Long prodId);
}
