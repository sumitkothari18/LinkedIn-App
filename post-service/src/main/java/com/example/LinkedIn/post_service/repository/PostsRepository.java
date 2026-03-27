package com.example.LinkedIn.post_service.repository;

import com.example.LinkedIn.post_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post,Long> {

    List<Post> getAllByUserId(Long userId);
}
