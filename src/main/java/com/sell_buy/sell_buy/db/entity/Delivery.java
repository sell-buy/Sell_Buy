package com.sell_buy.sell_buy.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


//delivery에서  findAllorderid를 이용하여 딜리버리 id == orderid tracking & carrier 를
// ajax통신을 통해 json리턴값을 확인 후 order table의 status를 비교하여 값이 같지않을 시 update 배달완료일시 패스

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
public class Delivery {
    @Id
    @Column(name = "ORDER_ID")
    private Integer orderId;
    @Column(name = "CARRIER")
    private String carrier;
    @Column(name = "TRACKING_NO")
    private String trackingNo;
}
