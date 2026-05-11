package com.example.LinkedIn.post_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreated {
    private Long createdByUserId;
    private Long postId;
    private Long userId;
    private String content;
}
