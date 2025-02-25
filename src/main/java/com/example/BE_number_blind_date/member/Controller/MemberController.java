package com.example.BE_number_blind_date.member.Controller;

import com.example.BE_number_blind_date.member.Dto.DtoLogin;
import com.example.BE_number_blind_date.member.Dto.DtoRegister;
import com.example.BE_number_blind_date.member.Service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
}
