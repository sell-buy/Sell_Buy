package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(@RequestParam(name = "catId", required = false) Long catId) {
        return ResponseEntity.ok(catId == null ? categoryService.getRootCategories() : categoryService.getSubCategories(catId));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategory(@RequestParam(name = "catId") Long catId) {
        if (catId == null) {
            return ResponseEntity.badRequest().body("catId is required");
        }
        if (!categoryService.existsById(catId)) {
            return ResponseEntity.badRequest().body("Category not found");
        }
        return ResponseEntity.ok(categoryService.getCategory(catId));
    }


}
