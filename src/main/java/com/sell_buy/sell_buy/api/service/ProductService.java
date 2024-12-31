package com.sell_buy.sell_buy.api.service;


import com.sell_buy.sell_buy.db.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Product registerProduct(Product product);

    Product updateProduct(Product product);

    boolean existsById(Long prodId);

    void deleteProduct(Long prodId);

    Product getProductbyId(Long prodId);

    Page<Product> getProductList(int page);

    Page<Product> getProductListByCategory(int page, String category);
}
