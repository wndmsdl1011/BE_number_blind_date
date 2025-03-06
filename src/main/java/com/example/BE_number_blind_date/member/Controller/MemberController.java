package com.example.BE_number_blind_date.member.Controller;

import com.example.BE_number_blind_date.member.Dto.DtoLogin;
import com.example.BE_number_blind_date.member.Dto.DtoMyPage;
import com.example.BE_number_blind_date.member.Dto.DtoRegister;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Service.MemberService;
import com.example.BE_number_blind_date.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    //  회원가입
    @PostMapping("/auth/register")
    public ResponseEntity<?> submitSignUpForm(@RequestBody DtoRegister dtoRegister) {

        ResponseEntity<?> response = memberService.register(dtoRegister);

        if(response.getStatusCode() == HttpStatus.CONFLICT){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이메일입니다.");
        }

        return response;
    }


    // 로그인 로직
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody DtoLogin dtoLogin, HttpServletResponse response) {

        ResponseEntity<?> loginResponse = memberService.login(dtoLogin, response);

        if(loginResponse.getStatusCode() == HttpStatus.NOT_FOUND){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이메일로 등록된 사용자가 없습니다.");
        }
        else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 올바르지 않습니다.");

        }

        return loginResponse;
    }

    // 로그아웃 로직
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refresh", required = false) String refreshToken,
                                    HttpServletResponse response) {
        return memberService.logout(refreshToken, response);
    }

    // 마이페이지 로직
    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(@RequestHeader(value = "Authorization", required = false) String token) {

        token = token.replace("Bearer ", "");

        if (jwtUtil.isExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
        }

        String userId = jwtUtil.getUsername(token);
        Optional<Member> memberData = memberService.getMyPageInfo(userId);

        if (memberData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        Member member = memberData.get();

        DtoMyPage response = new DtoMyPage(
                member.getEmail(),
                member.getUserName(),
                member.getNickname(),
                member.getGender(),
                member.getContact(),
                member.getAge(),
                member.getLocation()
        );

        return ResponseEntity.ok(response);
    }

    // 마이페이지 수정 로직
    @PutMapping("/user/profile")
    public ResponseEntity<?> updateMypage(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody DtoMyPage dtoMyPage) {


        token = token.replace("Bearer ", "");

        if (jwtUtil.isExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
        }

        String userId = jwtUtil.getUsername(token);
        DtoMyPage memberData = memberService.updateMypage(userId, dtoMyPage);

        if (memberData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }


        return ResponseEntity.ok(memberData);
    }

}
