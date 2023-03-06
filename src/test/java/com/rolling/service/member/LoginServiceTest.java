package com.rolling.service.member;

import com.rolling.domain.member.Member;
import com.rolling.domain.member.MemberRepository;
import com.rolling.domain.member.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.security.spec.ECField;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@AutoConfigureMockMvc // MockMvc 테스트를 위해 선언
@Transactional
@SpringBootTest
class LoginServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    /**
     * MockMvc 클래스를 이용해 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체.
     * MockMvc 객체를 이용하면 웹 브라우저에서 요청을 하는 것처럼 테스트를 할 수 있음.
     * */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    @AfterEach
    private void after(){
        em.clear();
    }

    private void clear(){
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void init(){
        memberRepository.save(Member.builder()
                .password(passwordEncoder.encode("123456789"))
                .nickname("ummchicken")
                .email("aaa@naver.com")
                .role(Role.USER)
                .build());
        clear();
    }

    @Test
    public void 로그인_성공() throws Exception {
        String email = "aaa@naver.com";
        String password = "123456789";
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/login")
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 로그인이 성공해 인증되었다면, 테스트코드가 통과한다.
    }

    @Test
    public void 로그인_실패_비밀번호_틀림() throws Exception {
        String email = "aaa@naver.com";
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/login")
                        .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
        /**
         * .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
         * 회원가입 시 입력한 비밀번호가 아닌 다른 비밀번호로 로그인을 시도하면,
         * 인증되지 않은 결과 값이 출력됨
         * */
    }

    @Test
    public void 로그인_실패_이메일_틑림() throws Exception {
        String email = "bbb@naver.com";
        String password = "123456789";
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/login")
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}