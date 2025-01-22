package com.sell_buy.sell_buy.api.controller;


import com.sell_buy.sell_buy.api.service.AuthenticationService;
import com.sell_buy.sell_buy.api.service.MemberService;
import com.sell_buy.sell_buy.common.exception.auth.AuthenticateNotMatchException;
import com.sell_buy.sell_buy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping()
    public ModelAndView memberInfo() {
        Member member = authenticationService.getAuthenticatedMember();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("memberInfo");
        modelAndView.addObject("member", member);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerMember() {
        return new ModelAndView("include/memberReg");
    }

    @GetMapping("/update")
    public ModelAndView updateMember() {
        return new ModelAndView("memberUpdate");
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


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Member member) {
        Long registeredMemberId;
        try {
            registeredMemberId = memberService.registerMember(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(200).body(registeredMemberId);
    }

    @GetMapping("/")
    public ModelAndView getMember() {
        Member member = authenticationService.getAuthenticatedMember();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("memberInfo");
        modelAndView.addObject("member", member);
        return modelAndView;
    }

    @PutMapping("/")
    public ResponseEntity<?> updateMember(@RequestBody Member member) throws AuthenticateNotMatchException {
        Member authMember = authenticationService.getAuthenticatedMember();
        if (authMember.getMemId().equals(member.getMemId())) {
            throw new AuthenticateNotMatchException("사용자가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(member.getPassword(), authMember.getPassword())) {
            throw new AuthenticateNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        memberService.updateMember(member);
        return ResponseEntity.status(200).body("Update Success");
    }

/* Not needed for using Spring Security
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member, HttpSession session) {
        System.out.println("login called");
        System.out.println(member.getLoginId());
        Long memId;
        try {
            memId = memberService.login(member);
            session.setAttribute("memId", memId);
            session.setMaxInactiveInterval(3600);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(200).body(memId);
    }
*/

}
