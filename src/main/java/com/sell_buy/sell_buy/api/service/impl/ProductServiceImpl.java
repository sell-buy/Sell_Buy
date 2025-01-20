package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import com.sell_buy.sell_buy.db.repository.MemberRepository;
import com.sell_buy.sell_buy.db.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    // This constructor is not needed because of Lombok's @RequiredArgsConstructor
    /*@Autowired
    public ProductServiceImpl(ProductRepository productRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    } */


    @Override
    public Product registerProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        if (productRepository.existsById(product.getProdId())) {
            return productRepository.save(product);

        } else {
            throw new EntityNotFoundException("Product with id " + product.getProdId() + " not found.");
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
    public Slice<Product> getProductList(int page, Long catId, String searchQuery, String searchType) {
        Pageable pageable = PageRequest.of(page - 1, 18);

        if (searchType == null) {
            if (catId == null) {
                return productRepository.findAllByOrderByCreateDateDesc(pageable);
            }
            return productRepository.findByCategoryOrderByCreateDateDesc(pageable, catId);
        }

        switch (searchType) {
            case "title+desc":
                if (catId == null) {
                    return productRepository.findByProdNameContainingOrProdDescContainingOrderByCreateDateDesc(pageable, searchQuery, searchQuery);
                }

                return productRepository.findByCategoryAndProdNameContainingOrProdDescContainingOrderByCreateDateDesc(pageable, catId, searchQuery, searchQuery);

            case "seller":
                Long sellerId = memberRepository.findByNickname(searchQuery).getMemId();
                if (catId == null) {
                    return productRepository.findBySellerIdOrderByCreateDateDesc(pageable, sellerId);
                }
                return productRepository.findByCategoryAndSellerIdOrderByCreateDateDesc(pageable, catId, sellerId);

            default:
                // searchQuery and searchType is always null when searchType is not "title+desc" or "seller"

                if (catId == null) {
                    return productRepository.findAllByOrderByCreateDateDesc(pageable);
                }

                return productRepository.findByCategoryOrderByCreateDateDesc(pageable, catId);
        }


    }

    public Page<Product> favoriteProductList(List<Long> prodIdList, int page) {
        Pageable pageable = PageRequest.of(page - 1, 18);

        return productRepository.findByProdIdInOrderByCreateDateDesc(pageable, prodIdList);
    }

}
