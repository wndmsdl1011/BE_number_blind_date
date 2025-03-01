package com.example.BE_number_blind_date.post.Controller;

import com.example.BE_number_blind_date.post.Service.PostService;
import com.example.BE_number_blind_date.post.dto.PostPage;
import com.example.BE_number_blind_date.post.dto.createPostDto;
import com.example.BE_number_blind_date.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

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

        // 토큰이 없음 -> 로그인되어 있지 않음
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않습니다.");
        }

        String accessToken = token.replace("Bearer ", "");
        String username = jwtUtil.getUsername(accessToken);

        ResponseEntity<?> response = postService.createPost(username, createPostDto);

        return response;

    }

    // 포스트익 목록 조회, 페이지당 3개
    @GetMapping("/posts")
    public ResponseEntity<Page<PostPage>> getPosts(@RequestHeader(value = "Authorization", required = false) String token,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostPage> posts = postService.getPosts(pageable);

        return ResponseEntity.ok(posts);
    }
}
