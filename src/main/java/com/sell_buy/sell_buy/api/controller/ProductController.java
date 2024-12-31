package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<?> registerProduct(@RequestBody Product product, HttpSession session) {
        Long seller_id = (Long) session.getAttribute("mem_id");
        if (seller_id == null) {
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }
        product.setSeller_id(seller_id);
        Product registeredProduct = productService.registerProduct(product);
        return ResponseEntity.status(200).body(product.getProd_id());
    }

    @PatchMapping("/{prod_id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, HttpSession session, @PathVariable("prod_id") Long prod_id) {
        Long seller_id = (Long) session.getAttribute("mem_id");
        if (seller_id == null) {
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }
        if (!productService.existsById(prod_id)) {
            return ResponseEntity.status(410).body("Product with id " + prod_id + " not found.");
        }

        product.setSeller_id(seller_id);
        product.setProd_id(prod_id);
        Product updatedProduct = productService.updateProduct(product);


        return ResponseEntity.status(200).body(product.getProd_id());
    }

    @DeleteMapping("/{prod_id}")
    public ResponseEntity<?> deleteProduct(HttpSession session, @PathVariable("prod_id") Long prod_id) {
        Long seller_id = (Long) session.getAttribute("mem_id");
        if (seller_id == null) {
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }
        if (seller_id != productService.getProductbyId(prod_id).getSeller_id()) {
            return ResponseEntity.status(412).body("User ID does not match the seller ID of the product.");
        }
        if (!productService.existsById(prod_id)) {
            return ResponseEntity.status(410).body("Product with id " + prod_id + " not found.");
        }

        productService.deleteProduct(prod_id);
        return ResponseEntity.status(200).body("Product with id " + prod_id + " deleted.");
    }

    @GetMapping("/{prod_id}")
    public ResponseEntity<?> getProductById(@PathVariable("prod_id") Long prod_id) {
        if (!productService.existsById(prod_id)) {
            return ResponseEntity.status(410).body("Product with id " + prod_id + " not found.");
        }
        return ResponseEntity.status(200).body(productService.getProductbyId(prod_id));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductList(@RequestParam(required = false, defaultValue = "1") int page) {
        if (page < 1) {
            return ResponseEntity.status(413).body("Page number must be greater than 0.");
        }

        Slice<Product> productList = productService.getProductList(page);
        return ResponseEntity.status(200).body(productList);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductList(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam long category) {
        if (page < 1) {
            return ResponseEntity.status(413).body("Page number must be greater than 0.");
        }

        Slice<Product> productList = productService.getProductList(page);
        return ResponseEntity.status(200).body(productList);
    }


}
