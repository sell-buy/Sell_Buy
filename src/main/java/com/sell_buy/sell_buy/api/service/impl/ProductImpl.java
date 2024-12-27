package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import com.sell_buy.sell_buy.db.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public Product registerProduct(Product product) {
        return productRepository.save(product);
    }
}
