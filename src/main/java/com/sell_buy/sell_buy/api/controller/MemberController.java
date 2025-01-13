package com.sell_buy.sell_buy.api.controller;


import com.sell_buy.sell_buy.api.service.MemberService;
import com.sell_buy.sell_buy.db.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String registerMember() {
        return "include/memberReg";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false, defaultValue = "false") String error,
                        @RequestParam(name = "loginId", required = false) String loginId, Model model) {
        if (error.equals("true")) {
            model.addAttribute("errorMessage", "로그인 실패, 다시 시도해주세요.");
        }
        model.addAttribute("member", loginId);
        return "include/login";
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody Member member) {
        Long registeredMemberId;
        try {
            registeredMemberId = memberService.registerMember(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(200).body(registeredMemberId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member, HttpSession session) {
        Long memId;
        try {
            memId = memberService.login(member);
            session.setAttribute("memId", memId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(200).body(memId);
    }
}
