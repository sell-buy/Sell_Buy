package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import com.sell_buy.sell_buy.db.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public Product registerProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        if (productRepository.existsById(product.getProd_id())) {
            return productRepository.save(product);

        } else {
            throw new EntityNotFoundException("Product with id " + product.getProd_id() + " not found.");
        }
    }

    @Override
    public boolean existsById(Long prodId) {
        return productRepository.existsById(prodId);
    }

    @Override
    public void deleteProduct(Long prodId) {
        productRepository.deleteById(prodId);
    }

    @Override
    public Product getProductbyId(Long prodId) {
        return productRepository.findById(prodId).orElseThrow(() -> new EntityNotFoundException("Product with id " + prodId + " not found."));
    }

    @Override
    public Page<Product> getProductList(int page) {
        PageRequest pageable = PageRequest.of(page - 1, 18);
        return productRepository.findAllByOrderByCreate_dateDesc(pageable);
    }

    @Override
    public Page<Product> getProductListByCategory(int page, String category) {
        PageRequest pageable = PageRequest.of(page - 1, 18);
        return;
    }

}
