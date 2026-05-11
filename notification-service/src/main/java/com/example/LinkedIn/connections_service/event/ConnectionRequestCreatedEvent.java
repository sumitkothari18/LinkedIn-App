package com.example.LinkedIn.connections_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class ConnectionRequestCreatedEvent {
    private Long receiverId;
    private Long senderId;

}
