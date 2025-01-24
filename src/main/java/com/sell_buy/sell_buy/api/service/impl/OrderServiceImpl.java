package com.sell_buy.sell_buy.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.db.entity.Delivery;
import com.sell_buy.sell_buy.db.entity.Member;
import com.sell_buy.sell_buy.db.entity.Order;
import com.sell_buy.sell_buy.db.entity.Product;
import com.sell_buy.sell_buy.db.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final CarrierRepository carrierRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    @Override
    public Long getSellOrder(long seller_id) {
        orderRepository.findBySellerId(seller_id);
        return null;
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
    public boolean hasExistOrderIdBySellerId(long sellerId) {
        return orderRepository.existsOrderIdBySellerId(sellerId);
    }

    @Override
    public List<Long> getAllOrderId() {
        return orderRepository.findAllIds();
    }

    // 배송상태 확인 후 업데이트
    @Scheduled(fixedRate = 3600000)
    @Override
    public void updateOrderStatus() {
        System.out.println("진입");
        List<Long> orderIds = getAllOrderId();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(orderIds.get(0).toString());
        for (Long order : orderIds) {
            long orderId = order;
            System.out.println("order : " + orderId);
            Order orderAttr = orderRepository.findByOrderId(orderId);
            String orderStatus = orderRepository.findOrderStatusByOrderId(orderId);
            String carrierStatus = orderRepository.findCarrierStatusByOrderId(orderId);
            System.out.println("상태 : " + orderStatus);
            System.out.println("속성 : " + carrierStatus);
            if (!orderStatus.equals("거래완료")) {
                System.out.println("거래완료가 아니면");
                Delivery delivery = deliveryRepository.findByOrderId(orderId);
                if (delivery != null) {
                    String carrierId = deliveryRepository.findByOrderId(orderId).getCarrierId();
                    String trackingNo = deliveryRepository.findByOrderId(orderId).getTrackingNo();
                    System.out.println(carrierId + "ddd" + trackingNo);
                    // Example https://apis.tracker.delivery/carriers/kr.epost/tracks/1111111111111
                    String url = String.format("https://apis.tracker.delivery/carriers/%s/tracks/%s", carrierId, trackingNo);
                    try {
                        System.out.println("여기는 들어옴??");
                        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                        String trackingInfo = response.getBody();
                        JsonNode rootNode = objectMapper.readTree(trackingInfo);
                        System.out.println(rootNode.toString());
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
                        System.out.println("마지막상태");
                        // 배송 시간
                        String rawTime = lastProgressNode.get("time").asText(); // "2024-06-04T15:50:00+09:00"
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
                        ZonedDateTime zonedDateTime = ZonedDateTime.parse(rawTime, formatter);
                        LocalDateTime lastTime = zonedDateTime.toLocalDateTime();
                        System.out.println(lastTime);
                        // 지역 위치
                        String lastLocation = lastProgressNode.get("location").path("name").asText();
                        System.out.println("오더의 배송 상태" + carrierStatus);
                        if (carrierStatus == null) {
                            orderAttr.setCarrierStatus(lastStatus);
                            System.out.println("배송값 저장 ");
                        } else {
                            if (carrierStatus.equals(lastStatus)) {
                                System.out.println(123);
                                log.debug(order.toString());
                            } else {
                                System.out.println(456);
                                orderAttr.setCarrierStatus(lastStatus);
                            }
                            System.out.println(lastStatus);
                            if (lastStatus.equals("배달완료")) {
                                orderAttr.setOrderStatus("거래완료");
                            }
                        }
                        orderRepository.save(orderAttr);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("배송이 없습니다");
                }
            }
        }
    }

    //상품 등록될때 자동으로 되는
    public
    @Override
    void updateProdOrder(Member member, String prodName) {
        //  memid를 넘겨줌
        // memberid를 받아서 product의 sellerid값 확인 , prodname으로prodid를 찾고 prodid로 orderid찾음
        Product prodList = productRepository.findByProdNameAndSellerId(prodName, member.getMemId());
        Order setOrder = new Order();
        setOrder.setSellerId(prodList.getSellerId());
        setOrder.setProdId(prodList.getProdId());
        setOrder.setOrderType(prodList.getProdType());
        setOrder.setCreatedDate(LocalDateTime.now());
        setOrder.setOrderStatus("거래전");
        orderRepository.save(setOrder);
        System.out.println("저장 완료");
    }

    //오더 택배사 선택 및 송장번호 등록
    @Override
    public void updateOrder(long orderId, int orderStatus, String carrier, String trackingNo) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setCarrier(carrier);
        delivery.setTrackingNo(trackingNo);
        deliveryRepository.save(delivery);

    }

    @Override
    public void updatePaymentStatus(String prodName, Member member) {
        Product prod = productRepository.findByProdName(prodName);
        Member buyer = memberRepository.findByMemId(member.getMemId());
        Order order1 = orderRepository.findByProdId(prod.getProdId());
        String addr = buyer.getAddress();
        String name = buyer.getName();
        String phone = buyer.getPhoneNum();
        order1.setBuyerId(buyer.getMemId());
        order1.setReceiverName(name);
        order1.setReceiverPhone(phone);
        order1.setReceiverAddress(addr);
        order1.setCreatedDate(LocalDateTime.now());
        order1.setOrderStatus("거래중");
        orderRepository.save(order1);
    }

}