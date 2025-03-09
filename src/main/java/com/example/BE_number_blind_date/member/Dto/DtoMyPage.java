package com.example.BE_number_blind_date.member.Dto;

import com.example.BE_number_blind_date.member.Entity.Major;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoMyPage {

    private String email;
    private String userName;
    private String gender;
    private String nickname;
    private String contact;
    private int age;
    private Major major;
    private String location;

    public DtoMyPage(String email,String userName, String nickname,  String gender, String contact, int age, Major major, String location) {
        this.email = email;
        this.userName = userName;
        this.nickname = nickname;
        this.gender = gender;
        this.contact = contact;
        this.age = age;
        this.major = major;
        this.location = location;
    }
}
