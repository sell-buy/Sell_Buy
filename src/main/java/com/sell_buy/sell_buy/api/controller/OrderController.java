package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.OrderService;
import com.sell_buy.sell_buy.db.entity.Order;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/register")
    public String registerOrder() {return "orderRegister";}

    @PostMapping("/register")
    public ResponseEntity<?> orderRegister(@RequestBody Order order, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("memId");
        if (sellerId == null) {
            return ResponseEntity.status(401
            ).body("OrderID IS NOT PRESENT IN THE SESSION");
        }
        order.setSellerId(sellerId);
        Order ordered = orderService.registerOrder(order);
        return ResponseEntity.status(200).body(order.getOrderId());
    }
    @DeleteMapping("/orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId,HttpSession session) {
        Long sellerId = (Long) session.getAttribute("memId");
        if (sellerId == null) {
            return ResponseEntity.status(200).body("deleteOrder Success");
        }
        return ResponseEntity.status(200).body("deleteOrder Success");
    }
}