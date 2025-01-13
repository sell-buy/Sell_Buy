package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Slice<Product> findAllByOrderByCreateDateDesc(Pageable pageable);

    Slice<Product> findByCategoryOrderByCreateDateDesc(Pageable pageable, Long category_id);

    Slice<Product> findByProdNameContainingOrProdDiscContainingOrderByCreateDateDesc(Pageable pageable, String searchQuery, String searchQuery1);

    Slice<Product> findByCategoryAndProdNameContainingOrProdDiscContainingOrderByCreateDateDesc(Pageable pageable, Long category, String searchQuery, String searchQuery1);

    Slice<Product> findBySellerIdOrderByCreateDateDesc(Pageable pageable, Long seller_id);

    Slice<Product> findByCategoryAndSellerIdOrderByCreateDateDesc(Pageable pageable, Long category, Long seller_id);
}