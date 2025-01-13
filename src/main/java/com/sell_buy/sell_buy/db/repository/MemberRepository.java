package com.sell_buy.sell_buy.db.repository;

import com.sell_buy.sell_buy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByNickname(String searchQuery);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNum(String phoneNum);

    Member findByLoginId(String loginId);
}
