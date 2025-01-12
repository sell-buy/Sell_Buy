package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.db.entity.Delivery;
import com.sell_buy.sell_buy.db.entity.Order;
import com.sell_buy.sell_buy.db.repository.DeliveryRepository;
import com.sell_buy.sell_buy.db.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceimpl  implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final DeliveryRepository deliveryRepository;
    @Autowired
    public OrderServiceimpl(OrderRepository orderRepository, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
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
        // 나중에
        return orderRepository.save(order);
    }

    @Override
    public Order changeOrderStatus(Order order) {
        List<Order> orders = orderRepository.findAllOrderId();
        for(Order o  : orders) {
            int status = o.getOrderStatus();
            long orderId = o.getOrderId();
            Delivery delivery = deliveryRepository.findByOrderId(orderId);
            String carrier  = delivery.getCarrier();
            String trackingNo = delivery.getTrackingNo();

            }
        return orderRepository.save(order);
    }

    @Override
    public boolean checkOrderId(boolean order_id) {
        return order_id;
    }

    @Override
    public Order cancelOrder(long order_id) {
        return orderRepository.deleteByOrderId(order_id);
    }


}
