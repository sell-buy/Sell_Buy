package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    void deleteByMemIdAndProdId(Long memId, Long prodId);

    boolean findByMemIdAndProdId(Long memId, Long prodId);

    void updateIsActivatedByMemIdAndProdId(Long memId, Long prodId, boolean b);

    boolean existsByMemIdAndProdIdAndIsActivated(Long memId, Long prodId, boolean b);

    Page<Favorite> findFavoriteList(Long memId, Pageable pageable);

    List<Long> findProdIdListByMemId(Long memId);
}
