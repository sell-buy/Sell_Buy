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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mem_seq")
    @SequenceGenerator(name = "mem_seq", sequenceName = "mem_seq", allocationSize = 1)
    private Long mem_id;
    private Long img_id;
    private Long login_id;
    private String name;
    private String nickname;
    private String address;
    private String email;
    private String create_date;
    private String phone_num;
    private boolean auth;
}
