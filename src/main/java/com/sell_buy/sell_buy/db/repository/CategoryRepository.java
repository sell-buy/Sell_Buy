package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    List<Category> findAllBySuperId(Long catId);

    List<Category> findAllBySuperIdIsNull();
}
