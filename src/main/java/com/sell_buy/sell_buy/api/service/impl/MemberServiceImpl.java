package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.MemberService;
import com.sell_buy.sell_buy.db.dao.mapper.MemberMapper;
import com.sell_buy.sell_buy.db.entity.Member;
import com.sell_buy.sell_buy.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;


    @Override
    public Member registerMember(Member member) {


        return memberRepository.save(member);
    }

    @Override
    public Member updateMember() {
        return null;
    }

    @Override
    public Member deleteMember() {
        return null;
    }

    @Override
    public Member getMemberByMemId(Long userId) {
        return null;
    }

    @Override
    public Member getMemberByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    @Override
    public List<Member> getAllMembers() {
        return List.of();
    }
}





