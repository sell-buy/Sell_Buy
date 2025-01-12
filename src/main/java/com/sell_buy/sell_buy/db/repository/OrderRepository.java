package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Delivery;
import com.sell_buy.sell_buy.db.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {
    Order findByOrderId(long orderId);
    Order findByBuyerId(long BuyerId);  //구매한 상품
    Order findBySellerId(long sellerId);
    Order deleteByOrderId(long orderId);

    List<Order> findAllOrderId();
    // findallorders로 아이디값들을 리스트형태로 찾은 후
    //  foreach문을 통해서 값들 확인 후 저장
    @EntityGraph(attributePaths = {"Delivery"})
    Optional<Order> findByOrderId(@Param("orderId") Long orderId);
    //이렇게 선언하면  Order&Delivery의 컬럼  이용가능


}

