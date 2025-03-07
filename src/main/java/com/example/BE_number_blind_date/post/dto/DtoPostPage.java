package com.example.BE_number_blind_date.post.dto;

import com.example.BE_number_blind_date.post.Entity.HobbyCategory;
import com.example.BE_number_blind_date.post.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class DtoPostPage {

        private Long user_id;    // 등록한 유저 ID
        private String nickname;
        private String contact;
        private String gender;
        private int age;
        private String mbti;
        private int height;
        private String  hobbies;
        private String highlight;

        public DtoPostPage(Post post) {
                this.user_id = post.getMember().getId();
                this.nickname = post.getMember().getNickname();
                this.contact = post.getMember().getContact();
                this.gender = post.getMember().getGender();
                this.age = post.getMember().getAge();
                this.mbti = post.getMbti();
                this.height = post.getHeight();

                this.hobbies = post.getHobbies().stream()
                        .map(HobbyCategory::getName)
                        .collect(Collectors.joining(", "));

                this.highlight = post.getHighlight();
        }
}
