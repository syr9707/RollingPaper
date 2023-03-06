package com.rolling.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolling.domain.member.Member;
import com.rolling.domain.member.MemberRepository;
import com.rolling.domain.member.Role;
import com.rolling.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 테스트를 위해 선언
@Transactional
class MemberControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

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

    @Autowired
    private ObjectMapper objectMapper;

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
    @WithMockUser(roles = "USER")
    public void 회원정보_수정_성공_닉네임변경() throws Exception {
        String updateNickname = "ummPizza";

        Member member = memberRepository.findByEmail("aaa@naver.com").orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지 않습니다.")
        );

        String memberId = String.valueOf(member.getId());
//        Long memberId = member.getId();

        Map<String, String> input = new HashMap<>();
        input.put("password", "123456789");
        input.put("nickname", updateNickname);

        mockMvc.perform(put("/member/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void 회원정보_삭제_성공() throws Exception {
        Member member = memberRepository.findByEmail("aaa@naver.com").orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지 않습니다.")
        );

        String memberId = String.valueOf(member.getId());

        mockMvc.perform(delete("/member/" + memberId))
                .andExpect(status().isOk())
                .andDo(print());

        Assertions.assertThat(memberRepository.findByEmail("aaa@naver.com").isEmpty());
    }

}