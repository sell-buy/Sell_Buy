package com.sell_buy.sell_buy.api.controller;

import com.sell_buy.sell_buy.api.service.AuthenticationService;
import com.sell_buy.sell_buy.api.service.FavoriteService;
import com.sell_buy.sell_buy.common.exception.product.ProductNotFoundException;
import com.sell_buy.sell_buy.db.entity.Member;
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
    private final AuthenticationService authenticationService;

    @GetMapping()
    public String favorite() {
        return "favorite";
    }

    @PostMapping()
    public ResponseEntity<?> toggleFavorite(HttpSession session, Long prodId) throws ProductNotFoundException {
        Member member = authenticationService.getAuthenticatedMember();
        Long memId = member.getMemId();
        if (prodId == null) {
            throw new ProductNotFoundException("Product ID is not present.");
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
        Member member = authenticationService.getAuthenticatedMember();
        Long memId = member.getMemId();
        return ResponseEntity.status(200).body(favoriteService.getFavoriteProductList(memId, page, size));
    }
}
