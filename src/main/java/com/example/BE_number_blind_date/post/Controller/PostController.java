package com.example.BE_number_blind_date.post.Controller;

import com.example.BE_number_blind_date.member.Dto.DtoLogin;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.post.Service.PostService;
import com.example.BE_number_blind_date.post.dto.createPostDto;
import com.example.BE_number_blind_date.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final JWTUtil jwtUtil;


    // 포스트익 생성
    @PostMapping("/post/create")
    public ResponseEntity<?> createPost(@RequestHeader(value = "Authorization", required = false) String token,
                                        @RequestBody createPostDto createPostDto) {

        log.info("postController token: " + token);
        log.info(createPostDto.getMbti());


        // 토큰이 없음 -> 로그인되어 있지 않음
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않습니다.");
        }

        String accessToken = token.replace("Bearer ", "");
        String username = jwtUtil.getUsername(accessToken);

        ResponseEntity<?> response = postService.createPost(username, createPostDto);


        return response;


    }

}
