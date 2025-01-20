package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.AWSFileService;
import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.common.utills.ListToJsonUtils;
import com.sell_buy.sell_buy.db.entity.Product;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AWSFileService awsFileService;

    @GetMapping("/register")
    public String registerProduct() {
        return "prodRegister";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@RequestPart("product") Product product,
                                             @RequestPart("images") List<MultipartFile> images,
                                             HttpSession session) throws IOException {
        Long sellerId = (Long) session.getAttribute("memId");
        if (sellerId == null) {
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }
        product.setSellerId(sellerId);
        
        if (images.size() > 4)
            return ResponseEntity.status(414).body("Maximum 4 images allowed.");


        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {

            String imageUrl = awsFileService.uploadFile(image);
            imageUrls.add(imageUrl);
        }
        product.setImageUrls(ListToJsonUtils.convertListToJson(imageUrls));

        productService.registerProduct(product);
        System.out.println(product.getProdId());
        return ResponseEntity.status(200).body(product.getProdId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, HttpSession session, @PathVariable("id") Long prodId) {
        System.out.println("updateProduct called with prodId: " + prodId);

        Long sellerId = (Long) session.getAttribute("memId");
        System.out.println("Session sellerId: " + sellerId);

        if (!productService.existsById(prodId)) {
            System.out.println("Product with id " + prodId + " not found.");
            return ResponseEntity.status(410).body("Product with id " + prodId + " not found.");
        }

        if (sellerId == null) {
            System.out.println("User ID is not present in the session.");
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }

        product.setSellerId(sellerId);
        product.setProdId(prodId);

        productService.updateProduct(product);

        System.out.println("Product updated successfully: " + product);
        return ResponseEntity.status(200).body(product.getProdId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(HttpSession session, @PathVariable("id") Long prodId) {
        if (session == null) {
            return ResponseEntity.status(411).body("Session is not present.");
        }
        Long sellerId = (Long) session.getAttribute("memId");
        if (sellerId == null) {
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }

        if (!productService.existsById(prodId)) {
            return ResponseEntity.status(410).body("Product with id " + prodId + " not found.");
        }
        if (sellerId != productService.getProductById(prodId).getSellerId()) {
            return ResponseEntity.status(412).body("User ID does not match the seller ID of the product.");
        }

        productService.deleteProduct(prodId);
        return ResponseEntity.status(200).body("Product with id " + prodId + " deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long prod_id) {
        if (!productService.existsById(prod_id)) {
            return ResponseEntity.status(410).body("Product with id " + prod_id + " not found.");
        }
        return ResponseEntity.status(200).body(productService.getProductById(prod_id));
    }


    @GetMapping("/list")
    public ResponseEntity<?> getProductList(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                            @RequestParam(name = "catId", required = false) Long catId,
                                            @RequestParam(name = "searchQuery", required = false) String searchQuery,
                                            @RequestParam(name = "searchType", required = false) String searchType) {
        if (page < 1) {
            return ResponseEntity.status(413).body("Page number must be greater than 0.");
        }

        Slice<Product> productList = productService.getProductList(page, catId, searchQuery, searchType);

        return ResponseEntity.status(200).body(productList);
    }


}
