package com.rolling.web.dto.member;

import com.rolling.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;
    private String password;
    private String email;
    private String nickname;

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
    }

}
