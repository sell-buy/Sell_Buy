package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/register")
    public String registerProduct() {
        return "prodRegister";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@RequestBody Product product, HttpSession session) {
        Long seller_id = (Long) session.getAttribute("user_id");
        product.setSeller_id(seller_id);
        Product registeredProduct = productService.registerProduct(product);
        return ResponseEntity.status(200).body(product.getProd_id());
    }
}
