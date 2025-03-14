package com.example.BE_number_blind_date.post.Controller;

import com.example.BE_number_blind_date.post.Service.PostService;
import com.example.BE_number_blind_date.post.Dto.DtoPostDetail;
import com.example.BE_number_blind_date.post.Dto.PostPageResponse;
import com.example.BE_number_blind_date.post.Dto.DtoCreatePost;
import com.example.BE_number_blind_date.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                                        @RequestBody DtoCreatePost DtoCreatePost) {

        // 토큰이 없음 -> 로그인되어 있지 않음
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않습니다.");
        }

        String accessToken = token.replace("Bearer ", "");
        String username = jwtUtil.getUsername(accessToken);

        ResponseEntity<?> response = postService.createPost(username, DtoCreatePost);

        return response;
    }

    // 포스트익 목록 조회, 페이지당 3개
    @GetMapping("/posts")
    public ResponseEntity<PostPageResponse> getPosts(@RequestParam(defaultValue = "1") int page,      // 기본값 0, Service 단에서 +1 설정
                                                   @RequestParam(defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of(page -1, size);
        PostPageResponse response = postService.getPosts(pageable);

        return ResponseEntity.ok(response);


    }

    // post 상세페이지
    @GetMapping("/post/{postId}")
    public ResponseEntity<DtoPostDetail> getPostDetail(@PathVariable Long postId) {

        DtoPostDetail postdata = postService.getPostDetail(postId);
        return ResponseEntity.ok(postdata);


    }


}
