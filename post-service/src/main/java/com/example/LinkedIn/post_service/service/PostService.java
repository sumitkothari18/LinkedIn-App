package com.example.LinkedIn.post_service.service;

import com.example.LinkedIn.post_service.dto.PostCreateRequestDto;
import com.example.LinkedIn.post_service.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPostsWithUserId(Long userId);
}
