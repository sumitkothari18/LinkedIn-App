package com.example.LinkedIn.notification_service.consumer;

import com.example.LinkedIn.connections_service.event.ConnectionRequestAcceptedEvent;
import com.example.LinkedIn.connections_service.event.ConnectionRequestCreatedEvent;
import com.example.LinkedIn.notification_service.entity.Notification;
import com.example.LinkedIn.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "connection_request_topic")
    public void handleConnectionRequest(ConnectionRequestCreatedEvent connectionRequestCreatedEvent)
    {
        log.info("handleConnectionRequest: {}",connectionRequestCreatedEvent);
        String message=String.format("User with id: %d has sent connection request",connectionRequestCreatedEvent.getSenderId());

        Notification notification= Notification.builder()
                .message(message)
                .userId(connectionRequestCreatedEvent.getReceiverId())
                .build();

        notificationService.addNotification(notification);
    }

    @KafkaListener(topics = "connection_accepted_topic")
    public void handleConnectionAccepted(ConnectionRequestAcceptedEvent connectionRequestAcceptedEvent)
    {
        log.info("handleConnectionAccepted: {}",connectionRequestAcceptedEvent);
        String message=String.format("User with id: %d has accepted your connection request",connectionRequestAcceptedEvent.getReceiverId());

        Notification notification= Notification.builder()
                .message(message)
                .userId(connectionRequestAcceptedEvent.getSenderId())
                .build();

        notificationService.addNotification(notification);
    }
}
