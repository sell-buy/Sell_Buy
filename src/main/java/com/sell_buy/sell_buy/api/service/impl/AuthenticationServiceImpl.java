package com.sell_buy.sell_buy.api.service.impl;

import com.sell_buy.sell_buy.api.service.AuthenticationService;
import com.sell_buy.sell_buy.db.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public Member getAuthenticatedMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated())
            throw new AuthenticationException("Unauthenticated user") {
            };

        return (Member) auth.getPrincipal();
    }
}
