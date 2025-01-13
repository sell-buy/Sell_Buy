package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.MemberService;
import com.sell_buy.sell_buy.db.dao.mapper.MemberMapper;
import com.sell_buy.sell_buy.db.entity.Member;
import com.sell_buy.sell_buy.db.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {


    private final MemberMapper memberMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public void createUser() {

    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

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





