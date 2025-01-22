package com.sell_buy.sell_buy.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "fav")
public class Favorite {
    @Column(name = "fav_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mem_seq")
    @SequenceGenerator(name = "mem_seq", sequenceName = "mem_seq", allocationSize = 1)
    private Long favId;
    @Column(name = "mem_id")
    private Long memId;
    @Column(name = "prod_id")
    private Long prod;
    @Column(name = "is_activated")
    private boolean isActivated;
}
