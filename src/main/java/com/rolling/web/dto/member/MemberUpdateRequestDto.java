package com.rolling.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    private String password;
    private String nickname;

    @Builder
    public MemberUpdateRequestDto(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

}
