package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(long orderId);
}
