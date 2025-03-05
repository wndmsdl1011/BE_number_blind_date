package com.example.BE_number_blind_date.member.Service;

import com.example.BE_number_blind_date.member.Dto.*;
import com.example.BE_number_blind_date.member.Entity.RefreshEntity;
import com.example.BE_number_blind_date.member.Repository.RefreshRepository;
import com.example.BE_number_blind_date.member.Role.Role;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Repository.MemberRepository;
import com.example.BE_number_blind_date.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;

    // 회원가입
    public ResponseEntity<?> register(DtoRegister dtoRegister) {

        // 이메일 중복검사 Http 상태코드 (409 conflict)
        if (memberRepository.existsByEmail(dtoRegister.getEmail())) {
            System.out.println("이메일 중복!");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT.value()));
        }

            String encodedPassword = passwordEncoder.encode(dtoRegister.getUserPassword());

            Member member = Member.builder()
                    .email(dtoRegister.getEmail())
                    .userName(dtoRegister.getUserName())
                    .userPassword(encodedPassword)
                    .gender(dtoRegister.getGender())
                    .nickname(dtoRegister.getNickname())
                    .contact(dtoRegister.getContact())
                    .age(dtoRegister.getAge())
                    .location(dtoRegister.getLocation())
                    .role(Role.USER)
                    .build();

            memberRepository.save(member);
            
            // 회원가입 성공 Http 상태코드 (201 created)
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MemberResponse("회원가입 완료", HttpStatus.CREATED.value(), member.getEmail(), member.getUserName()));
    }

    // 로그인
    public ResponseEntity<?> login(DtoLogin dtoLogin, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(dtoLogin.getEmail()).orElse(null);

        // DB에 사용자가 없음 Http 상태코드 (404 Not Found)
        if(member == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 이메일로 등록된 사용자가 없습니다.");
        }

        // 비밀번호가 일치하지 않음 Http 상태코드 (401 Unauthorized)
        else if (!passwordEncoder.matches(dtoLogin.getUserPassword(), member.getUserPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // Access Token, Refress Token 발급
        String accessToken = jwtUtil.createJwt("access", member.getEmail(), member.getRole().toString(), 600000L);
        String refreshToken = jwtUtil.createJwt("refresh", member.getEmail(), member.getRole().toString(), 86400000L);

        log.info("accessToken: " + accessToken);    // 얘는 프론트에 보내서 관리
        log.info("refreshToken: " + refreshToken);  // 얘는 백엔드에서 관리해야함


        // Refresh Token 저장
        Optional<RefreshEntity> existingToken = refreshRepository.findByUsername(member.getEmail());

        // 기존 데이터가 있으면 업데이트
        if (existingToken.isPresent()) {
            RefreshEntity refreshEntity = existingToken.get();
            refreshEntity.setRefresh(refreshToken);
            refreshEntity.setExpiration("86400000");
            refreshRepository.save(refreshEntity);
        } 
        // 기존 데이터가 없어 새로 저장
        else {
            refreshRepository.save(new RefreshEntity(member.getEmail(), refreshToken, "86400000"));
        }
        
        // 프론트 보낼 accessToken 헤더에 저장
        response.setHeader("Authorization", "Bearer " + accessToken);
        // 쿠키에 refreshToken 저장
        response.setHeader("Set-Cookie", "refresh=" + refreshToken + "; Path=/; HttpOnly; Secure; SameSite=None");

        System.out.println("로그인한 사용자:" + dtoLogin.getEmail());
        return ResponseEntity.ok("로그인 성공");
    }

    // 로그아웃
    @Transactional
    public ResponseEntity<?> logout(String refreshToken, HttpServletResponse response) {
        log.info("로그아웃 요청 - RefreshToken: {}",refreshToken);
        
        // RefreshToken이 DB에 존재하는지?
        Optional<RefreshEntity> existingToken = refreshRepository.findByRefresh(refreshToken);

        if (existingToken.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 요청: Refresh Token이 존재하지 않습니다.");
        }

        // refreshToken 삭제
        refreshRepository.deleteByRefresh(refreshToken);

        // refreshToken 쿠키 삭제
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        log.info("로그아웃 성공");
        return ResponseEntity.ok("로그아웃 되었습니다.");

    }

    // 마이페이지 로직
    public Optional<Member> getMyPageInfo(String userId) {
        Optional<Member> member=  memberRepository.findByEmail(userId);
        return member;
    }

    // 마이페이지 수정 로직
    public Optional<Member> updateMypage(String userId) {

        Optional<Member> memberdata=  memberRepository.findByEmail(userId);

        return memberdata;
    }
}
