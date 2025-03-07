package com.example.BE_number_blind_date.post.dto;

import com.example.BE_number_blind_date.post.Entity.Post;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostPageResponse {


    private int page;         // 현재 페이지
    private int total_pages;  // 전체 페이지 수
    private int total_items;  // 전체 아이템 개수
    private List<DtoPostPage> postits;

    public PostPageResponse(int page, int totalPages, int totalItems, List<Post> posts) {
        this.page = page;
        this.total_pages = totalPages;
        this.total_items = totalItems;
        this.postits = posts.stream()
                .map(DtoPostPage::new)
                .collect(Collectors.toList());
    }
}
