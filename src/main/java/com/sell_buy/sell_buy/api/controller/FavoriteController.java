package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fav")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping()
    public String favorite() {
        return "favorite";
    }

    @PostMapping()
    public ResponseEntity<?> toggleFavorite(HttpSession session, Long prodId) {
        Long memId = (Long) session.getAttribute("memId");
        if (memId == null) {
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }
        if (prodId == null) {
            return ResponseEntity.status(412).body("Product ID is not present in the request.");
        }
        if (favoriteService.wasFavorite(memId, prodId)) {
            if (favoriteService.isFavorite(memId, prodId)) {
                favoriteService.deactivateFavorite(memId, prodId);
                return ResponseEntity.status(200).body("Favorite deactivated.");
            } else {
                favoriteService.activateFavorite(memId, prodId);
                return ResponseEntity.status(200).body("Favorite activated.");
            }
        } else {
            favoriteService.addFavorite(memId, prodId);
            return ResponseEntity.status(200).body("Favorite added.");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getFavoriteList(HttpSession session, int page, int size) {
        Long memId = (Long) session.getAttribute("memId");
        if (memId == null) {
            return ResponseEntity.status(411).body("User ID is not present in the session.");
        }
        return ResponseEntity.status(200).body(favoriteService.getFavoriteProductList(memId, page, size));
    }
}
