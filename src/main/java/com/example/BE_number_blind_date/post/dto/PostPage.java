package com.example.BE_number_blind_date.post.dto;

import com.example.BE_number_blind_date.post.Entity.HobbyCategory;
import com.example.BE_number_blind_date.post.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostPage {

        private int page;         // 현재 페이지
        private int totalPages;  // 전체 페이지 수
        private int totalItems;  // 전체 아이템 개수

        private Long userId;    // 등록한 유저 ID

        private String nickname;
        private String contact;
        private int age;
        private String mbti;
        private int height;
        private List<HobbyCategory> hobbies;
        private String highlight;

        public PostPage(Post post) {
                this.userId = post.getMember().getId();
                this.nickname = post.getMember().getNickname();
                this.contact = post.getMember().getContact();
                this.age = post.getMember().getAge();
                this.mbti = post.getMbti();
                this.height = post.getHeight();
                this.hobbies = post.getHobbies();
                this.highlight = post.getHighlight();
        }


}
