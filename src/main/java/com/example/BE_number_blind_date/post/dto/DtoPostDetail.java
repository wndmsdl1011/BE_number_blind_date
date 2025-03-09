package com.example.BE_number_blind_date.post.dto;


import com.example.BE_number_blind_date.member.Entity.Major;
import com.example.BE_number_blind_date.post.Entity.HobbyCategory;
import com.example.BE_number_blind_date.post.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DtoPostDetail {

    private String nickname;
    private String gender;
    private int age;
    private Major major;
    private String mbti;
    private int height;
    private String hobbies;
    private String highlight;


    public DtoPostDetail(Post post) {
        this.nickname = post.getMember().getNickname();
        this.gender = post.getMember().getGender();
        this.age = post.getMember().getAge();
        this.major = post.getMember().getMajor();
        this.mbti = post.getMbti();
        this.height = post.getHeight();

        this.hobbies = post.getHobbies().stream()
                .map(HobbyCategory::getName)
                .collect(Collectors.joining(", "));

        this.highlight = post.getHighlight();
    }
}
