package com.example.BE_number_blind_date.member.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DtoLogin {

    @Email(message = "잘못된 이메일 형식입니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하로 입력해야 합니다.")
    private String userPassword;
}

