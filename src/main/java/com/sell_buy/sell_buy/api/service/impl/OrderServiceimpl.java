package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.db.entity.Order;
import com.sell_buy.sell_buy.db.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceimpl  implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceimpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void getSellOrder(long seller_id) {
    }

    @Override
    public void getBuyOrder(long buyer_id) {

    }
    @Override
    public void getOrderId(long order_id) {

    }

    @Override
    public void setOrderId(long order_id) {

    }

    @Override
    public Order registerOrder(Order order) {
        return orderRepository.save(order);
    }

}
