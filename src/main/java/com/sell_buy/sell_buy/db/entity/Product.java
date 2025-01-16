package com.sell_buy.sell_buy.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
    @SequenceGenerator(name = "prod_seq", sequenceName = "prod_seq", allocationSize = 1)
    @Column(name = "prod_id")
    private long prodId;
    @Column(name = "seller_id")
    private long sellerId;
    @Column(name = "prod_name")
    private String prodName;
    @Column(name = "prod_disc")
    private String prodDesc;
    @Column(name = "is_auction")
    private boolean isAuction;
    private int price;
    @Column(name = "is_available")
    private boolean isAvailable;
    @Column(name = "category_id")
    private long category;
    @Column(name = "prod_type")
    private int prodType;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "img_urls")
    private String imageUrls;

    @PrePersist
    protected void onCreate() {
        if (createDate == null)
            createDate = LocalDateTime.now();
        System.out.println("createDate: " + createDate);
    }
}
