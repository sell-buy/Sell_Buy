package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Slice<Product> findAllByOrderByCreate_dateDesc(PageRequest pageable);

    Slice<Product> findByCategory_IDByCreate_dateDesc(PageRequest pageable, Long category_id);

    Slice<Product> findByProd_nameContainingOrProd_descContainingOrderByCreate_dateDesc(PageRequest pageable, String searchQuery, String searchQuery1);

    Slice<Product> findByCategory_IDAndProd_nameContainingOrProd_descContainingOrderByCreate_dateDesc(PageRequest pageable, Long category, String searchQuery, String searchQuery1);

    Slice<Product> findBySeller_idByCreate_dateDesc(PageRequest pageable, Long seller_id);

    Slice<Product> findByCategory_IDAndSeller_idByCreate_dateDesc(PageRequest pageable, Long category, Long seller_id);
}