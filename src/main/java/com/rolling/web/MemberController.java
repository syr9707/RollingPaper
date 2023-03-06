package com.rolling.web;

import com.rolling.domain.member.Member;
import com.rolling.domain.member.MemberRepository;
import com.rolling.service.member.MemberService;
import com.rolling.web.dto.member.MemberSaveRequestDto;
import com.rolling.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
//@RestController
@Controller
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    public final MemberRepository memberRepository;

    @GetMapping("/")
    public @ResponseBody String index() {
        return "index 페이지 입니다.";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/loginForm";
    }

//    @PostMapping("/login")
//    public void login() {
//
//    }

    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("MemberSaveRequestDto", new MemberSaveRequestDto());
        return "member/signUpForm";
    }

    @PostMapping("/signUp")
    public @ResponseBody Long signUp(@Valid  MemberSaveRequestDto memberSaveRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Error");
        }

        return memberService.saveMember(memberSaveRequestDto);
    }
    
    @PutMapping("/member/{memberId}")
    public @ResponseBody Long update(@PathVariable("memberId") long memberId, @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {

        return memberService.update(memberId, memberUpdateRequestDto);
    }

    @DeleteMapping("/member/{memberId}")
    public @ResponseBody Long delete(@PathVariable("memberId") long memberId) {
        memberService.delete(memberId);

        return memberId;
    }

}
