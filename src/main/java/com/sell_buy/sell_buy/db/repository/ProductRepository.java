package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
