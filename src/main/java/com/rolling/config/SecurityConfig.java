package com.rolling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable() // formLogin 인증방법 비활성화 (json을 통해 로그인 진행)
                .httpBasic().disable() // httpBasic 인증방법 비활성화(특정 리소스에 접근할 때 username과 password 물어봄)
                .csrf().disable() // Spring Security는 csrf 토큰 없이 요청하면 해당 요청을 막음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests() // HttpServletRequest에 따라 접근을 제한한다.
//                .antMatchers("/**").permitAll(); // 모든 url에 대해서 접근할 수 있도록 허용
                .antMatchers("/login", "/signUp","/").permitAll() // antMatchers() 메소드로 특정 경로를 지정하며, permitAll(),hasRole() 메소드로 권한에 따른 접근 설정을 한다.
                .anyRequest().authenticated(); // 그 외의 경로는 인증된 사용자만이 접근이 가능하다.

        return http.build();
    }

    // 비밀번호 암호화 저장
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
