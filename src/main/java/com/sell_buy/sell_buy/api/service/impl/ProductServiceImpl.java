package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import com.sell_buy.sell_buy.db.repository.MemberRepository;
import com.sell_buy.sell_buy.db.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }


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
    public Product getProductById(Long prodId) {
        return productRepository.findById(prodId).orElseThrow(() -> new EntityNotFoundException("Product with id " + prodId + " not found."));
    }

    @Override
    public Slice<Product> getProductList(int page, Long category, String searchQuery, String searchType) {
        PageRequest pageable = PageRequest.of(page - 1, 18);


        switch (searchType) {
            case "title+desc":
                if (category == null) {
                    return productRepository.findByProd_nameContainingOrProd_descContainingOrderByCreate_dateDesc(pageable, searchQuery, searchQuery);
                }

                return productRepository.findByCategory_IDAndProd_nameContainingOrProd_descContainingOrderByCreate_dateDesc(pageable, category, searchQuery, searchQuery);

            case "seller":
                Long sellerId = memberRepository.findByNickname(searchQuery).getMem_id();
                if (category == null) {
                    return productRepository.findBySeller_idByCreate_dateDesc(pageable, sellerId);
                }
                return productRepository.findByCategory_IDAndSeller_idByCreate_dateDesc(pageable, category, sellerId);

            default:
                // searchQuery and searchType is always null

                if (category == null) {
                    return productRepository.findAllByOrderByCreate_dateDesc(pageable);
                }

                return productRepository.findByCategory_IDByCreate_dateDesc(pageable, category);
        }

    }

}
