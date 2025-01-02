package com.sell_buy.sell_buy.api.service;

import com.sell_buy.sell_buy.db.entity.Member;

import java.util.List;

public interface MemberService {

    void createUser();

    void updateUser();

    void deleteUser();

    Member getMemberByMemId(Long userId);

    Member getMemberByNickname(String nickname);

    List<Member> getAllMembers();
}
