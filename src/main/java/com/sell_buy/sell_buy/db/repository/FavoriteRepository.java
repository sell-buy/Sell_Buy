package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Favorite;
import jakarta.transaction.Transactional;
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
    void deleteByMemIdOrProd(Long memId, Long prodId);

    boolean existsByMemIdAndProd(Long memId, Long prodId);

    @Transactional
    @Modifying
    @Query("update Favorite f set f.isActivated = :isActivated where f.memId = :memId and f.prod = :prodId")
    void updateIsActivatedByMemIdAndProdId(@Param("memId") Long memId, @Param("prodId") Long prodId, @Param("isActivated") boolean b);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorite f WHERE f.memId = :memId AND f.prod = :prodId AND f.isActivated = :isActivated")
    boolean existsByMemIdAndProdIdAndIsActivated(@Param("memId") Long memId, @Param("prodId") Long prodId, @Param("isActivated") boolean isActivated);

    @Query("select f from Favorite  f where f.memId = :memId and f.isActivated = true")
    Page<Favorite> findFavoriteList(@Param("memId") Long memId, Pageable pageable);

    @Query("select f.prod from Favorite f where f.memId = :memId")
    List<Long> findProdIdListByMemId(@Param("memId") Long memId);

    void deleteByProd(Long prodId);

    void deleteByMemId(Long memId);

    @Query("select f.prod from Favorite f where f.memId = :memId and f.isActivated = :activated")
    List<Long> findProdIdListByMemIdAndIsActivated(Long memId, boolean activated);
}
