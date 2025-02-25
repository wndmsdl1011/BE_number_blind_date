package com.example.BE_number_blind_date.post.Repository;

import com.example.BE_number_blind_date.post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
