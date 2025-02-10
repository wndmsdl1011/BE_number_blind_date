package com.example.BE_number_blind_date.member.Controller;

import com.example.BE_number_blind_date.member.Dto.DtoRegister;
import com.example.BE_number_blind_date.member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        return ResponseEntity.ok("회원가입이 성공적으로 처리되었습니다.");
    }


}
