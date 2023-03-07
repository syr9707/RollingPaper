package com.rolling.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void 패스워드_암호화_encodeWithBcryptTest() throws Exception {
        // given
        String password = "ummchicken";

        // when
        String encodePassword = passwordEncoder.encode(password);

        // then
        assertThat(encodePassword).isNotEqualTo(password);
    }

    // 암호화시 항상 랜덤한 salt를 통한 암호화 -> 항상 다른 결과가 반환됨
    @Test
    void 패스워드_랜덤_암호화() throws Exception {
        // given
        String password = "ummchicken";

        // when
        String encodePassword = passwordEncoder.encode(password);
        String encodePassword2 = passwordEncoder.encode(password);

        // then
        assertThat(encodePassword).isNotEqualTo(encodePassword2);
    }

    @Test
    void 암호화된_비밀번호_매치() throws Exception {
        // given
        String password = "ummchicken";

        // when
        String encodePassword = passwordEncoder.encode(password);

        // then
        assertThat(passwordEncoder.matches(password, encodePassword)).isTrue();
    }

}
