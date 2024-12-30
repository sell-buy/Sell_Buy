package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(Long userId);
}
