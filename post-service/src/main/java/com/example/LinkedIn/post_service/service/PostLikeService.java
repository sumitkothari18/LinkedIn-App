package com.example.LinkedIn.post_service.service;

import org.springframework.http.ResponseEntity;

public interface PostLikeService {


    void likePost(Long postId, Long userId);

    void unlikePost(Long postId, Long userId);
}
