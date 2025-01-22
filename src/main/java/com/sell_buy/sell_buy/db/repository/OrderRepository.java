package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Order findByOrderId(long orderId);

    void findByBuyerId(long BuyerId);  //구매한 상품

    void findBySellerId(long sellerId);

    boolean existsOrderIdBySellerId(long sellerId);

//    List<Order> orderId(long orderId);
}
