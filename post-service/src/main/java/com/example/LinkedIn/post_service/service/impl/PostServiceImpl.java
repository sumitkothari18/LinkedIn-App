package com.example.LinkedIn.post_service.service.impl;

import com.example.LinkedIn.post_service.auth.UserContextHolder;
import com.example.LinkedIn.post_service.cleints.ConnectionsClient;
import com.example.LinkedIn.post_service.dto.PersonDto;
import com.example.LinkedIn.post_service.dto.PostCreateRequestDto;
import com.example.LinkedIn.post_service.dto.PostDto;
import com.example.LinkedIn.post_service.entity.Post;
import com.example.LinkedIn.post_service.exception.ResourceNotFoundException;
import com.example.LinkedIn.post_service.repository.PostsRepository;
import com.example.LinkedIn.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;

    @Override
    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {

        Post post=modelMapper.map(postCreateRequestDto,Post.class);
        post.setUserId(userId);

        postsRepository.save(post);

        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        log.info("Retrieving post with id :{}",postId);

        Long userId= UserContextHolder.getCurrentUserId();
        List<PersonDto> firstConnections=connectionsClient.getFirstConnections();
        Post post=postsRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: "+postId)
        );

        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostsWithUserId(Long userId) {

        List<Post> posts=postsRepository.getAllByUserId(userId);
        return posts.stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());
    }
}
