package com.sell_buy.sell_buy.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.db.entity.Delivery;
import com.sell_buy.sell_buy.db.entity.Order;
import com.sell_buy.sell_buy.db.repository.DeliveryRepository;
import com.sell_buy.sell_buy.db.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class OrderServiceimpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private Order order;

    public OrderServiceimpl(OrderRepository orderRepository, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
    }


    @Override
    public void getSellOrder(long seller_id) {
        orderRepository.findBySellerId(seller_id);
    }

    @Override
    public void getBuyOrder(long buyer_id) {
        orderRepository.findByBuyerId(buyer_id);
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

    @Override
    public void deleteOrder(long order_id) {
        orderRepository.deleteById(order_id);
    }

    @Override
    public boolean hasExistOrder(Long orderId) {
        return orderRepository.existsById(orderId);
    }

    @Override
    public List<Order> getAllOrderId() {
        return List.of();
    }


    @Scheduled(fixedRate = 3600000)
    @Override
    public void updateOrderStatus() {
        List<Order> orderIds = getAllOrderId();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Order order : orderIds) {
            long orderId = order.getOrderId();
            Delivery delivery = deliveryRepository.findByOrderId(orderId);
            if (delivery != null) {
                String carrierId = deliveryRepository.findByOrderId(orderId).getCarrierId();
                String trackingNo = deliveryRepository.findByOrderId(orderId).getTrackingNo();
                String url = String.format("https://apis.tracker.delivery/carriers/%s/tracks/%s", carrierId, trackingNo);
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                    String trackingInfo = response.getBody();
                    JsonNode rootNode = objectMapper.readTree(trackingInfo);
                    // 어디서
                    JsonNode fromNode = rootNode.get("from");
                    // 누가
                    String fromName = fromNode.get("name").asText();
                    // 보낸 시간
                    String fromTime = fromNode.get("time").asText();
                    // last of progresses
                    JsonNode progressesNode = rootNode.get("progresses");
                    // 가장 최신 배송상태로 업데이트
                    JsonNode lastProgressNode = progressesNode.get(progressesNode.size() - 1);
                    // 배송상태
                    String lastStatus = lastProgressNode.get("status").path("text").asText();
                    // 배송 시간
                    LocalDateTime lastTime = LocalDateTime.parse(lastProgressNode.get("time").asText());
                    // 지역 위치
                    String lastLocation = lastProgressNode.get("location").path("name").asText();
                    if (order.getCarrierStatus().equals(lastStatus)) {
                        log.debug(order.toString());
                    } else {
                        order.setCarrierStatus(lastStatus);
                    }
                    orderRepository.save(order);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}