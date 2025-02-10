package com.example.BE_number_blind_date.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity      // 기본 보안 구성을 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests((auth) -> auth
                                .anyRequest().permitAll()  // 모든 경로에 대한 접근 허용
                        // To Do: 계층별 권한 구조 작성해야함 환자
                        // Example
//                        .requestMatchers("/auth/register").permitAll()
//                        .requestMatchers("/").hasAnyRole("A", "B", "C")
//                        .requestMatchers("/manager").hasAnyRole("B", "C")
//                        .requestMatchers("/admin").hasAnyRole("C")
//                        .anyRequest().authenticated()
                );

        http
                .csrf((auth) -> auth.disable());

        return http.build();



    }


    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
