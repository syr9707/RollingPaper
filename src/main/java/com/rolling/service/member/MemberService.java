package com.rolling.service.member;

import com.rolling.domain.member.Member;
import com.rolling.domain.member.MemberRepository;
import com.rolling.web.dto.member.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 저장
    @Transactional
    public Long saveMember(MemberSaveRequestDto memberSaveRequestDto) {
        validateDuplicateMember(memberSaveRequestDto);
        return memberRepository.save(memberSaveRequestDto.toEntity()).getId();
    }

    /**
     * 회원가입 중복 검사
     * 이메일 중복 불가능
     * */
    private void validateDuplicateMember(MemberSaveRequestDto memberSaveRequestDto) {

        if(memberRepository.findByEmail(memberSaveRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

}
