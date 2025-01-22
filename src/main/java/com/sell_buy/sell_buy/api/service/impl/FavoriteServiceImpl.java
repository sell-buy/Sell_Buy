package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.FavoriteService;
import com.sell_buy.sell_buy.db.entity.Favorite;
import com.sell_buy.sell_buy.db.entity.Product;
import com.sell_buy.sell_buy.db.repository.FavoriteRepository;
import com.sell_buy.sell_buy.db.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favRepo;
    private final ProductRepository productRepository;

    @Override
    public Favorite addFavorite(Long memId, Long prodId) {
        Favorite fav = Favorite.builder()
                .memId(memId)
                .prod(prodId)
                .build();

        return favRepo.save(fav);
    }

    @Override
    public void deactivateFavorite(Long memId, Long prodId) {
        favRepo.updateIsActivatedByMemIdAndProdId(memId, prodId, false);
    }

    @Override
    public void activateFavorite(Long memId, Long prodId) {
        favRepo.updateIsActivatedByMemIdAndProdId(memId, prodId, true);
    }

    @Override
    public boolean isFavorite(Long memId, Long prodId) {
        return favRepo.existsByMemIdAndProdIdAndIsActivated(memId, prodId, true);
    }


    @Override
    public boolean wasFavorite(Long memId, Long prodId) {
        return favRepo.existsByMemIdAndProd(memId, prodId);
    }

    @Override
    public Page<Product> getFavoriteProductList(Long memId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        List<Long> prodIdList = favRepo.findProdIdListByMemId(memId);

        return productRepository.findByProdIdInOrderByCreateDateDesc(prodIdList, pageable);
    }

    @Override
    public void deleteFavorite(Long memId, Long prodId) {
        if (prodId == null) {
            favRepo.deleteByMemId(memId);
        } else if (memId == null) {
            favRepo.deleteByProd(prodId);
        } else {
            favRepo.deleteByMemIdOrProd(memId, prodId);
        }
    }
}
