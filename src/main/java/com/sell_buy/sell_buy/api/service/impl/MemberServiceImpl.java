package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.MemberService;
import com.sell_buy.sell_buy.api.vo.MemberVO;
import com.sell_buy.sell_buy.db.dao.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {


    private final MemberMapper memberMapper;

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
    public void getUserByUserId(Long userId) {

    }

    @Override
    public List<MemberVO> getAllUsers() {

        return memberMapper.selectAllMembers();
    }
}
