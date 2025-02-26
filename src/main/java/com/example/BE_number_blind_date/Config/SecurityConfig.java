package com.example.BE_number_blind_date.Config;

import com.example.BE_number_blind_date.member.Repository.RefreshRepository;
import com.example.BE_number_blind_date.util.JWTFilter;
import com.example.BE_number_blind_date.util.JWTUtil;
import com.example.BE_number_blind_date.util.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import com.example.BE_number_blind_date.member.Service.MemberService;

import java.util.Collections;

@Configuration
@EnableWebSecurity      // 기본 보안 구성을 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;



    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MemberService memberService) throws Exception {

        // cors 설정
        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();

                                // 허용할 포트
                                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);    // 쿠키 포함 허용
                                configuration.setMaxAge(3600L);

                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                                configuration.setExposedHeaders(Collections.singletonList("Authorization"));


                                return configuration;
                            }
                        }));

        // csrf 비활성화
        http
                .csrf((auth) -> auth.disable());
        http
                .formLogin((auth) -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());


        // 인가설정 *편의를 위해 일단 모두 허용
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/auth/register", "/auth/login", "/reissue", "/auth/logout","/post/create").permitAll()
                        .anyRequest().authenticated());

        //JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);    // addFilterAt 대신해서 작성하기 때문에 -> At



        // 세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
