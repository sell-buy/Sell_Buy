package com.sell_buy.sell_buy.api.controller;


import com.sell_buy.sell_buy.api.service.MemberService;
import com.sell_buy.sell_buy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memberValidator);
    }

    @GetMapping("/register")
    public String registerMember() {
        return "memberReg";
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody Member member, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Member registeredMember = memberService.registerMember(member);
        return ResponseEntity.status(200).body(registeredMember);
    }
}
