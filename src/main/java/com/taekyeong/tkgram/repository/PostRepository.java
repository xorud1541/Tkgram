package com.taekyeong.tkgram.repository;

import com.taekyeong.tkgram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPoster(Long poster);
}
