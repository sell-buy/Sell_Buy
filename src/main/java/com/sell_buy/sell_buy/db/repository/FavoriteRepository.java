package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    void deleteByMemIdAndProdId(Long memId, Long prodId);

    boolean findByMemIdAndProdId(Long memId, Long prodId);

    @Modifying
    @Query("update Favorite f set f.isActivated = :isActivated where f.memId = :memId and f.prodId = :prodId")
    void updateIsActivatedByMemIdAndProdId(@Param("memId") Long memId, @Param("prodId") Long prodId, @Param("isActivated") boolean b);

    @Query("select f.prodId from Favorite f where f.memId = :memId and f.prodId = :prodId and f.isActivated = :isActivated")
    boolean existsByMemIdAndProdIdAndIsActivated(@Param("memId") Long memId, @Param("prodId") Long prodId, @Param("isActivated") boolean b);

    @Query("select f from Favorite  f where f.memId = :memId and f.isActivated = true")
    Page<Favorite> findFavoriteList(@Param("memId") Long memId, Pageable pageable);

    List<Long> findProdIdListByMemId(Long memId);
}
