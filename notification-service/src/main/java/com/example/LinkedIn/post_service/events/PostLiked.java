package com.example.LinkedIn.post_service.events;

import lombok.Builder;
import lombok.Data;

@Data
public class PostLiked {

    private Long postId;
    private Long postOwnerId;
    private Long postLikedUserId;
}
