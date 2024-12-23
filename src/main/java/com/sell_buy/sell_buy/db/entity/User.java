package com.sell_buy.sell_buy.db.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class User {
}
