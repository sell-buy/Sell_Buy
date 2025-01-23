package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.AuthenticationService;
import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.*;
import com.sell_buy.sell_buy.db.repository.MemberRepository;
import com.sell_buy.sell_buy.db.repository.OrderRepository;
import com.sell_buy.sell_buy.db.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final AuthenticationService authenticationService;
    private final OrderService orderService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

//    @GetMapping("/")
//    public void updateState() {
//        orderService.updateOrderStatus();
//    }

    @GetMapping("/register")
    public String registerOrder() {
        return "orderRegister";
    }

    @PostMapping("/register")
    public ResponseEntity<?> orderRegister(@RequestBody Product product, HttpSession session, HttpServletRequest request) {
        Member member = authenticationService.getAuthenticatedMember();
        String requestURI = request.getRequestURI();
        if (member == null) {
            return ResponseEntity.status(400).body("Login First");
        } else {
            //        Long sellerId = member.getMemId();
            //prod_id / buyer_id
            String prodName = product.getProdName();
            Product product1 = productRepository.findByProdName(prodName);
            Order order = new Order();
            order.setSellerId(product1.getSellerId());
            order.setProdId(product1.getProdId());
            order.setOrderType(product1.getProdType());
            order.setCreatedDate(product1.getCreateDate());
            orderService.registerOrder(order);
            return ResponseEntity.status(200).body(order.getOrderId());

        }
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId, HttpSession session) {
        Member member = authenticationService.getAuthenticatedMember();
        Long sellerId = member.getMemId();
        Long orderSellerId = orderService.getSellOrder(sellerId);
//        판매자아이디가 없을시
//        오더번호가 존재하지않을 때
        if (orderService.hasExistOrder(orderId)) {
            return ResponseEntity.status(400).body("OrderId IS NOT EXIST");
        }
        // 팬마자가 가진 오더가 아닐 때
        if (!orderService.hasExistOrderIdBySellerId(sellerId)) {
            return ResponseEntity.status(400).body("OrderId Seller is not matched");
        } else {
            orderService.deleteOrder(orderId);
            return ResponseEntity.status(200).body("deleteOrder Success");
        }
    }

    //    택배사 등록   업데이트
    @PutMapping("/put/{prodName}") // orderid
    public ResponseEntity<?> putOrder(@PathVariable String prodName, @RequestBody Delivery delivery, @RequestBody Order order, @RequestBody Carrier carrier, HttpSession session,
                                      @RequestHeader(value = "Referer", required = false) String referer) {
        Member member = authenticationService.getAuthenticatedMember();
        if (referer != null && referer.contains("payment")) {

        }
        if (referer != null && referer.contains("order")) {

        }
        Member memAttrList = memberRepository.findByMemId(order.getBuyerId());
        Product prod = productRepository.findByProdName(prodName);
        Order order1 = orderRepository.findByOrderId(order.getOrderId());
        String addr = memAttrList.getAddress();
        String name = memAttrList.getName();
        String phone = memAttrList.getPhoneNum();
        order1.setProdId(prod.getProdId());
        order1.setSellerId(prod.getSellerId());
        order1.setBuyerId(order1.getBuyerId());
        order1.setReceiverAddress(addr);
        order1.setReceiverName(name);
        order1.setReceiverPhone(phone);
        order1.setOrderType(prod.getProdType());
        order1.setCreatedDate(LocalDateTime.now());
        orderService.registerOrder(order1);
        order1.setOrderStatus("거래중");
        Long sellerId = member.getMemId();
        int orderType = order1.getOrderType();
        String carrierName = carrier.getCarrierName();
        String trackingNo = delivery.getTrackingNo();
        if (orderService.hasExistOrder(order1.getOrderId())) {
            return ResponseEntity.status(400).body("OrderId IS not EXIST");
        }
        if (orderService.hasExistOrderIdBySellerId(sellerId)) {
            return ResponseEntity.status(400).body("OrderId Seller is not matched");
        } else {
            orderService.updateProdOrder(order1.getOrderId(), orderType, carrierName, trackingNo);
            return ResponseEntity.status(200).body("updateOrder Success");
        }
    }
}