package com.example.BE_number_blind_date.member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private String message;
    private int statusCode;
    private String email;
    private String userName;
}
