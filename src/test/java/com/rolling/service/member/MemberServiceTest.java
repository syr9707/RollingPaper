package com.rolling.service.member;

import com.rolling.domain.member.Member;
import com.rolling.domain.member.MemberRepository;
import com.rolling.domain.member.Role;
import com.rolling.web.dto.member.MemberSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

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

    private MemberSaveRequestDto memberSaveRequestDto() {
        return new MemberSaveRequestDto("12345678", "aaa@naver.com", "myNickname", Role.USER);
    }

    @Test
    public void 회원가입_테스트() {
        // given
        MemberSaveRequestDto memberSaveRequestDto = memberSaveRequestDto();

        // when
        memberService.saveMember(memberSaveRequestDto);
        clear();

        // then
        Member member = memberRepository.findByEmail(memberSaveRequestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 회원 없습니다. email = " + memberSaveRequestDto.getEmail())
        );

        assertThat(member.getId()).isNotNull();
        assertThat(member.getEmail()).isEqualTo(memberSaveRequestDto.getEmail());
        assertThat(member.getNickname()).isEqualTo(memberSaveRequestDto.getNickname());
        assertThat(member.getRole()).isSameAs(Role.USER);
    }

    @Test
    public void 중복_회원가입_이메일중복() {
        // given
        MemberSaveRequestDto memberSaveRequestDto = memberSaveRequestDto();
        memberService.saveMember(memberSaveRequestDto);
        clear();

        // when, then
        assertThat(assertThrows(Exception.class,
                () -> memberService.saveMember(memberSaveRequestDto)));

    }

    @Test
    public void 회원가입_실패_입력하지않은_필드가있으면_오류() {
        // given
        MemberSaveRequestDto memberSaveRequestDto1 = new MemberSaveRequestDto(null, "aaa@naver.com", "nickname", Role.USER);
        MemberSaveRequestDto memberSaveRequestDto2 = new MemberSaveRequestDto(passwordEncoder.encode("12345678!!"), null, "nickname", Role.USER);
        MemberSaveRequestDto memberSaveRequestDto3 = new MemberSaveRequestDto(passwordEncoder.encode("12345678!!"), "aaa@naver.com", null, Role.USER);

        // when, then
        assertThrows(Exception.class,
                () -> memberService.saveMember(memberSaveRequestDto1));

        assertThrows(Exception.class,
                () -> memberService.saveMember(memberSaveRequestDto2));

        assertThrows(Exception.class,
                () -> memberService.saveMember(memberSaveRequestDto3));
    }

}