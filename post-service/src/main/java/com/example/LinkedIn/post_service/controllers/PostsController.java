package com.example.LinkedIn.post_service.controllers;

import com.example.LinkedIn.post_service.dto.PostCreateRequestDto;
import com.example.LinkedIn.post_service.dto.PostDto;
import com.example.LinkedIn.post_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto , HttpServletRequest httpServletRequest)
    {
        PostDto postDto=postService.createPost(postCreateRequestDto,1L);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId)
    {
        PostDto postDto=postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsWithUserId(@PathVariable Long userId)
    {
        List<PostDto> postDtos=postService.getAllPostsWithUserId(userId);
        return ResponseEntity.ok(postDtos);
    }

}
