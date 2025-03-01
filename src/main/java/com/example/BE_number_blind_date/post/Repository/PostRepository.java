package com.example.BE_number_blind_date.post.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BE_number_blind_date.post.Entity.Post;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findAll(Pageable pageable);
}

