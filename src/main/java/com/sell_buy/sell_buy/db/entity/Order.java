package com.sell_buy.sell_buy.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Setter //setter 이용시 인스턴스값이 어디서 꼬일지 모름
@Builder // 객체만들어서 생성  OPEN -> BUILDER() , CLOSE -> BUILD()
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="ORDER_SEQ" )
    @SequenceGenerator(name = "ORDER_SEQ",sequenceName = "ORDER_SEQ",allocationSize = 1)
    private int ORDER_ID;
    private int SELLER_ID;
    private int BUYER_ID;
}
