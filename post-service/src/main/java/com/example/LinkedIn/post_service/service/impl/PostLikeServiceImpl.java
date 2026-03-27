package com.example.LinkedIn.post_service.service.impl;

import com.example.LinkedIn.post_service.entity.Post;
import com.example.LinkedIn.post_service.entity.PostLike;
import com.example.LinkedIn.post_service.exception.BadRequestException;
import com.example.LinkedIn.post_service.exception.ResourceNotFoundException;
import com.example.LinkedIn.post_service.repository.PostLikeRepository;
import com.example.LinkedIn.post_service.repository.PostsRepository;
import com.example.LinkedIn.post_service.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;


    @Override
    public void likePost(Long postId, Long userId) {

        log.info("Attempting to like the post with id: {}",postId);

        boolean exists=postsRepository.existsById(postId);
        if(!exists)
        {
            throw new ResourceNotFoundException("Post not found with id: "+postId);
        }

        boolean alreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(alreadyLiked)
        {
            throw new BadRequestException("Cannot like the same post again");
        }

        PostLike postLike=new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);

        postLikeRepository.save(postLike);

        log.info("Post liked with id: {}",postId);



    }

    @Override
    @Transactional
    public void unlikePost(Long postId, Long userId) {
        log.info("Attempting to unlike the post with id: {}",postId);

        boolean exists=postsRepository.existsById(postId);
        if(!exists)
        {
            throw new ResourceNotFoundException("Post not found with id: "+postId);
        }

        boolean alreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!alreadyLiked)
        {
            throw new BadRequestException("Cannot unlike the post which is not liked");
        }

        postLikeRepository.deleteByUserIdAndPostId(userId,postId);

        log.info("Post with id: {} unliked successfully",postId);
    }
}
