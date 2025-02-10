package com.example.BE_number_blind_date.member.Service;

import com.example.BE_number_blind_date.member.Role.Role;
import com.example.BE_number_blind_date.member.Dto.DtoRegister;
import com.example.BE_number_blind_date.member.Dto.ErrorResponse;
import com.example.BE_number_blind_date.member.Dto.MemberResponse;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
                    .birthDate(dtoRegister.getBirthDate().atStartOfDay())
                    .location(dtoRegister.getLocation())
                    .role(Role.USER)
                    .build();

            memberRepository.save(member);
            
            // 회원가입 성공 Http 상태코드 (201 created)
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MemberResponse("회원가입 완료", HttpStatus.CREATED.value(), member.getEmail(), member.getUserName()));
    }

}
