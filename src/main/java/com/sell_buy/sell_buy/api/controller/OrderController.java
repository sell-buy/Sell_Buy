package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.db.entity.Order;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    OrderService orderService;
//    @GetMapping("/")
//    public void updateState() {
//        orderService.updateOrderStatus();
//    }

    @GetMapping("/register")
    public String registerOrder() {
        return "orderRegister";
    }
    @PostMapping("/register")
    public ResponseEntity<?> orderRegister(@RequestBody Order order, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("memId");
//        아이디값 없을떄
        if (sellerId == null) {
            return ResponseEntity.status(401
            ).body("OrderID IS NOT PRESENT IN THE SESSION");
        }
        order.setSellerId(sellerId);
        orderService.registerOrder(order);
        return ResponseEntity.status(200).body(order.getOrderId());
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("memId");
//        판매자아이디가 없을시
        if (sellerId == null) {
            return ResponseEntity.status(401).body("UserID IS NOT PRESENT IN THE SESSION");
        }
//        오더번호가 존재하지않을 때
        if (orderService.hasExistOrder(orderId)) {
            return ResponseEntity.status(400).body("OrderId IS NOT EXIST");
        } else {
            orderService.deleteOrder(orderId);
            return ResponseEntity.status(200).body("deleteOrder Success");
        }
    }

    //    배송상태 업데이트
    @PutMapping("/{orderId}/put")
    public ResponseEntity<?> putOrder(@PathVariable Long orderId, @RequestBody Order order, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("memId");
        return null;
    }
}