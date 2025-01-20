package com.sell_buy.sell_buy.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sell_buy.sell_buy.api.service.AWSFileService;
import com.sell_buy.sell_buy.api.service.AuthenticationService;
import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.common.utills.JsonUtils;
import com.sell_buy.sell_buy.db.entity.Member;
import com.sell_buy.sell_buy.db.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AWSFileService awsFileService;
    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public ModelAndView registerProduct() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("prodRegister");
        return modelAndView;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@RequestPart("product") Product product,
                                             @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        Member member = authenticationService.getAuthenticatedMember();

        Long sellerId = member.getMemId();

        product.setSellerId(sellerId);

        if (images.size() > 4)
            return ResponseEntity.status(414).body("Maximum 4 images allowed.");

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageUrl = awsFileService.uploadFile(image);
            imageUrls.add(imageUrl);
        }
        product.setImageUrls(JsonUtils.convertListToJson(imageUrls));

        productService.registerProduct(product);
        System.out.println(product.getProdId());
        return ResponseEntity.status(200).body(product.getProdId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable("id") Long prodId) {
        System.out.println("updateProduct called with prodId: " + prodId);

        Member member = authenticationService.getAuthenticatedMember();

        Long sellerId = member.getMemId();
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
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long prodId) {
        Member member = authenticationService.getAuthenticatedMember();

        Long sellerId = member.getMemId();
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
    public ModelAndView getProductById(@PathVariable("id") Long prodId, HttpServletResponse response)
            throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ObjectMapper objectMapper = new ObjectMapper();
        Member member = authenticationService.getAuthenticatedMember();
        boolean isSeller = false;

        if (!productService.existsById(prodId)) {
            modelAndView.setViewName("exception");
            modelAndView.addObject("errorCode", 410);
            modelAndView.addObject("errorMessage", "상품이 존재하지 않습니다.");

            response.setStatus(HttpServletResponse.SC_GONE);
            return modelAndView;
        }

        Product product = productService.getProductById(prodId);

        // 판매자인지 확인
        if (member != null) {
            Long sellerId = member.getMemId();
            if (sellerId != null && sellerId == productService.getProductById(prodId).getSellerId()) {
                isSeller = true;
            }
        }

        List<String> imageUrls = JsonUtils.convertJsonToList(product.getImageUrls());

        modelAndView.setViewName("prodSpec");
        modelAndView.addObject("product", product);
        modelAndView.addObject("isSeller", isSeller);
        modelAndView.addObject("imageUrls", imageUrls);
        response.setStatus(HttpServletResponse.SC_OK);
        return modelAndView;
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
