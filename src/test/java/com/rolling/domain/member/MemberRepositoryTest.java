package com.rolling.domain.member;

import com.rolling.web.dto.member.MemberSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

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

    @Test
    public void 회원저장_성공() throws Exception {
        // given
        Member member = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();

        // when
        Member saveMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(saveMember.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없습니다. userId = " + saveMember.getId()));

        assertThat(findMember).isSameAs(saveMember);
        assertThat(findMember).isSameAs(member);
    }

    @Test
    public void 오류_회원가입시_이메일이_없음() throws Exception {
        // given
        Member member = Member.builder()
                .password("1234567")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();

        // when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    public void 오류_회원가입시_닉네임이_없음() throws Exception {
        // given
        Member member = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .role(Role.USER)
                .build();

        // when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    public void 오류_회원가입시_중복된_이메일이_있음_unique() throws Exception {
        // given
        Member member1 = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();
        Member member2 = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();

        memberRepository.save(member1);
        clear();

        // when, then
        assertThrows(Exception.class, () -> memberRepository.save(member2));
    }

    @Test
    public void 오류_회원가입시_중복된_이메일이_있음_unique_다른필드는다름() throws Exception {
        // given
        Member member1 = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();
        Member member2 = Member.builder()
                .password("1234567777")
                .email("aaa@naver.com")
                .nickname("ummchicken222")
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member1);
        clear();

        // when, then
        assertThrows(Exception.class, () -> memberRepository.save(member2));
    }

    @Test
    public void 성공_회원정보수정() throws Exception {
        // given
        Member member = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        clear();

        String updatePassword = "updatePassword";
        String updateNickname = "updateNickname";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // when
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없습니다. memberId = " + member.getId()));
        findMember.updatePassword(passwordEncoder, updatePassword);
        findMember.updateNickname(updateNickname);
        em.flush();

        // then
        Member updateMember = memberRepository.findById(findMember.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없습니다. userId = " + findMember.getId()));

        assertThat(updateMember).isSameAs(findMember);
        assertThat(passwordEncoder.matches(updatePassword, updateMember.getPassword())).isTrue();
        assertThat(updateMember.getNickname()).isEqualTo(updateNickname);
        assertThat(updateMember.getNickname()).isNotEqualTo(member.getNickname());
    }

    @Test
    public void 성공_회원삭제() throws Exception {
        // given
        Member member = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();

        memberRepository.delete(member);
        clear();

        // then
        assertThrows(Exception.class, () -> memberRepository.findById(member.getId())
                .orElseThrow(() -> new Exception()));
    }

    @Test
    public void findByEmail() throws Exception {
        // given
        String email = "aaa@naver.com";
        Member member = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        clear();

        // when, then
        assertThat(memberRepository.findByEmail(email).get().getNickname()).isEqualTo(member.getNickname());
        assertThat(memberRepository.findByEmail(email).get().getId()).isEqualTo(member.getId());
        assertThrows(Exception.class, () -> memberRepository.findByEmail(email+"123")
                .orElseThrow(() -> new Exception()));
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2023, 2, 1, 0, 0, 0);
        Member member = Member.builder()
                .password("1234567")
                .email("aaa@naver.com")
                .nickname("ummchicken")
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        clear();

        // when
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없습니다. memberId = " + member.getId()));

        // then
        assertThat(findMember.getCreatedDate().isAfter(now));
        assertThat(findMember.getModifiedDate().isAfter(now));
    }

}