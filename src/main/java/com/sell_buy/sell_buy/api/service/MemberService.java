package com.sell_buy.sell_buy.api.service;

import com.sell_buy.sell_buy.db.entity.Member;

import java.util.List;

public interface MemberService {


    Member registerMember(Member member);

    Member updateMember();

    Member deleteMember();

    Member getMemberByMemId(Long userId);

    Member getMemberByNickname(String nickname);

    List<Member> getAllMembers();
}
