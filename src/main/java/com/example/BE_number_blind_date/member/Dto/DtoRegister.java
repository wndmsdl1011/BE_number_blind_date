package com.example.BE_number_blind_date.member.Dto;

import com.example.BE_number_blind_date.member.Role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRegister {

    @Email(message = "잘못된 이메일 형식입니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하로 입력해야 합니다.")
    private String userPassword;

    @NotBlank(message = "성별은 필수 항목입니다.")
    @Pattern(regexp = "^(남성|여성)$", message = "성별은 '남성' 또는 '여성'만 입력 가능합니다.")
    private String gender;

    @Min(value = 18, message = "나이는 18세 이상이어야 합니다.")
    @Max(value = 99, message = "나이는 99세 이하이어야 합니다.")
    private int age;

    @NotBlank(message = "지역은 필수 항목입니다.")
    private String location;

    private Role role;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    private String nickname;

    @NotBlank(message = "연락처는 필수 항목입니다.")
    private String contact;

    @NotBlank(message = "MBTI는 필수 항목입니다.")
    @Size(min = 4, max = 4, message = "MBTI는 4자로 입력해야 합니다.")
    private String mbti;

    @Min(value = 100, message = "키는 100cm 이상이어야 합니다.")
    @Max(value = 250, message = "키는 250cm 이하이어야 합니다.")
    private int height;

    private String hobby;

    private String highlight;
}
