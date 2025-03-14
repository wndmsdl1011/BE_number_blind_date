package com.example.BE_number_blind_date.post.Service;

import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Repository.MemberRepository;
import com.example.BE_number_blind_date.post.Entity.Post;
import com.example.BE_number_blind_date.post.Repository.PostRepository;
import com.example.BE_number_blind_date.post.Dto.DtoPostDetail;
import com.example.BE_number_blind_date.post.Dto.PostPageResponse;
import com.example.BE_number_blind_date.post.Dto.DtoCreatePost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;


import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<?> createPost(String username, DtoCreatePost DtoCreatePost) {

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));


        Post post = new Post();
        post.setMember(member);
        post.setMbti(DtoCreatePost.getMbti());
        post.setHeight(DtoCreatePost.getHeight());
        post.setHobbies(Optional.ofNullable(DtoCreatePost.getHobbies()).orElse(Collections.emptyList()));
        post.setHighlight(DtoCreatePost.getHighlight());

        postRepository.save(post);



        return ResponseEntity.ok("포스트잇 생성 완료");
    }

    // post 목록 조회
    public PostPageResponse getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return new PostPageResponse(
                pageable.getPageNumber() + 1,
                posts.getTotalPages(),
                (int) posts.getTotalElements(),
                posts.getContent()
        );
    }

    // post 상세정보
    public DtoPostDetail getPostDetail(Long postId) {
        return postRepository.findById(postId)
                .map(DtoPostDetail::new)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
    }


}




