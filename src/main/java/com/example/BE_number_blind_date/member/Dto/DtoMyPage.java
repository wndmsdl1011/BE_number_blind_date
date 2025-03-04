package com.example.BE_number_blind_date.member.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoMyPage {

    private String email;
    private String userName;
    private String gender;
    private String nickName;
    private String contact;
    private int age;
    private String location;

    public DtoMyPage(String email,String userName, String nickName,  String gender, String contact, int age, String location) {
        this.email = email;
        this.userName = userName;
        this.nickName = nickName;
        this.gender = gender;
        this.contact = contact;
        this.age = age;
        this.location = location;
    }
}
