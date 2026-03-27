package com.example.LinkedIn.post_service.controllers;

import com.example.LinkedIn.post_service.service.PostLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likeAPost(@PathVariable Long postId , HttpServletRequest httpServletRequest)
    {
        postLikeService.likePost(postId,1L);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId,HttpServletRequest httpServletRequest)
    {
        postLikeService.unlikePost(postId,1L);
        return ResponseEntity.noContent().build();
    }

}
