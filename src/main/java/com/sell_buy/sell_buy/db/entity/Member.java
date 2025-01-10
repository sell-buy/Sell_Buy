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
    @Column(name = "mem_id")
    private Long memId;
    @Column(name = "img_id")
    private Long imgId;
    @Column(name = "login_id", unique = true)
    private String loginId;
    private String password;
    private String name;
    @Column(unique = true)
    private String nickname;
    private String address;
    @Column(unique = true)
    private String email;
    @Column(name = "create_date")
    private String createDate;
    @Column(name = "phone_num", unique = true)
    private String phoneNum;
    @Column(name = "auth")
    private boolean role;


}
