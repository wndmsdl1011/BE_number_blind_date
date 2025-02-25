package com.example.BE_number_blind_date.post.Controller;

import com.example.BE_number_blind_date.member.Dto.DtoRegister;
import com.example.BE_number_blind_date.post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 포스트익 생성
//    @PostMapping("/post/create")
//    public ResponseEntity<?> createPost(@RequestBody DtoPost dtoPost) {
//
//
//    }
}
