package com.example.BE_number_blind_date.post.Service;

import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Repository.MemberRepository;
import com.example.BE_number_blind_date.post.Entity.Post;
import com.example.BE_number_blind_date.post.Repository.PostRepository;
import com.example.BE_number_blind_date.post.dto.createPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<?> createPost(String username, createPostDto createPostDto) {

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));


        Post post = new Post();
        post.setMember(member);
        post.setMbti(createPostDto.getMbti());
        post.setHeight(createPostDto.getHeight());
        post.setHobbies(Optional.ofNullable(createPostDto.getHobbies()).orElse(Collections.emptyList()));
        post.setHighlight(createPostDto.getHighlight());

        postRepository.save(post);



        return ResponseEntity.ok("포스트잇 생성 완료");
    }
}
