package com.example.LinkedIn.notification_service.consumer;

import com.example.LinkedIn.notification_service.entity.Notification;
import com.example.LinkedIn.notification_service.service.NotificationService;
import com.example.LinkedIn.post_service.events.PostCreated;
import com.example.LinkedIn.post_service.events.PostLiked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreated postCreated)
    {
        log.info("Received notification :{}",postCreated);
        String message=String.format("Your connection with id: %d has created this post: %s",postCreated.getCreatedByUserId(),postCreated.getContent());
        Notification notification=Notification.builder()
                .message(message)
                .userId(postCreated.getUserId())
                .build();

        notificationService.addNotification(notification);
    }

    @KafkaListener(topics = "post_liked_topic")
    public void handlePostLiked(PostLiked postLiked)
    {
        log.info("Received Notification: {}",postLiked);
        String message=String.format("Your connection with id: %d has liked post with id : %d",postLiked.getPostLikedUserId(),postLiked.getPostId());
        Notification notification=Notification.builder()
                .message(message)
                .userId(postLiked.getPostOwnerId())
                .build();

        notificationService.addNotification(notification);
    }
}
