package com.sell_buy.sell_buy.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;
    private final ObjectMapper objectMapper; // ObjectMapper 주입

    @GetMapping("/")
    public ModelAndView test() throws JsonProcessingException { // JsonProcessingException 예외 처리 추가
        System.out.println("Hello World"); // TODO: Remove this line

        Slice<Product> productListSlice = productService.getProductList(1, null, null, null);
        List<Product> productList = productListSlice.getContent();

        // 이미지 URL 처리 로직 추가
        List<Product> processedProductList = new ArrayList<>();
        for (Product product : productList) {
            String imageUrlsJson = product.getImageUrls();
            List<String> imageUrls = new ArrayList<>();
            if (imageUrlsJson != null && !imageUrlsJson.isEmpty()) {
                // JSON 파싱 오류 방지: try-catch 블록 추가
                try {
                    imageUrls = objectMapper.readValue(imageUrlsJson, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
                    if (!imageUrls.isEmpty()) {
                        product.setImageUrls(imageUrls.get(0)); // 첫 번째 이미지 URL 만 저장 (setImageUrls 메소드를 재활용 - setter 이름이 오해를 일으킬 수 있지만, 편의상 재활용)
                    } else {
                        product.setImageUrls(null); // 이미지 URL 없는 경우 null 설정 (setter 재활용)
                    }
                } catch (JsonProcessingException e) {
                    product.setImageUrls(null); // JSON 파싱 실패 시 null 설정 (setter 재활용)
                }
            } else {
                product.setImageUrls(null); // imageUrlsJson 이 null 이거나 비어있는 경우 null 설정 (setter 재활용)
            }
            processedProductList.add(product);
        }


        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("productList", processedProductList); // 가공된 상품 목록 전달
        return modelAndView;
    }
}