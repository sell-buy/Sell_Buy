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
    private long prod_id;
    private long seller_id;
    private String prod_name;
    private String prod_disc;
    private boolean is_auction;
    private int price;
    private boolean is_available;
    private String category;
    private LocalDateTime create_date;
}
