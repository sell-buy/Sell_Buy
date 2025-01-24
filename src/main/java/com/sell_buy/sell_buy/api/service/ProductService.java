package com.sell_buy.sell_buy.api.service;


import com.sell_buy.sell_buy.db.entity.Order;
import com.sell_buy.sell_buy.db.entity.Product;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductService {
    Product registerProduct(Product product);

    Product updateProduct(Product product);

    boolean existsById(Long prodId);

    void deleteProduct(Long prodId);

    Product getProductById(Long prodId);


    Slice<Product> getProductList(int page, int size, Long catId, String searchQuery, String searchType);

    List<Product> getProductListByOrderList(List<Order> orderList);
}
