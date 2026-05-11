package com.example.LinkedIn.connections_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class ConnectionRequestAcceptedEvent {

    private Long senderId;
    private Long receiverId;

}
