package com.example.LinkedIn.connections_service.service.impl;


import com.example.LinkedIn.connections_service.auth.UserContextHolder;
import com.example.LinkedIn.connections_service.entity.Person;
import com.example.LinkedIn.connections_service.event.ConnectionRequestAcceptedEvent;
import com.example.LinkedIn.connections_service.event.ConnectionRequestCreatedEvent;
import com.example.LinkedIn.connections_service.exceptions.BadExceptionError;
import com.example.LinkedIn.connections_service.repository.PersonRepository;
import com.example.LinkedIn.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsServiceImpl implements ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, ConnectionRequestCreatedEvent> connectionRequestCreatedEventKafkaTemplate;
    private final KafkaTemplate<Long, ConnectionRequestAcceptedEvent> connectionRequestAcceptedEventKafkaTemplate;

    @Override
    public List<Person> getFirstConnections(Long userId) {

        log.info("Getting first degree connections for user with userId: {}",userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

    @Override
    public Person getByName(String name) {
        log.info("Getting person with name : {}",name);

        return personRepository.getByName(name);
    }

    @Override
    public List<Person> getSecondConnections(Long userId) {
        log.info("Getting first degree connections for user with userId: {}",userId);

        return personRepository.getSecondDegreeConnections(userId);
    }

    @Override
    public void sendRequest(Long receiverId) {
        Long senderId=UserContextHolder.getCurrentUserId();
        log.info("Sending connection request with senderId: {}, receiverId:{}",senderId,receiverId);
        if(senderId.equals(receiverId))
        {
            throw new BadExceptionError("Both sender and receiver are same");
        }

        boolean alreadySentRequest=personRepository.connectionRequestExists(senderId,receiverId);
        if(alreadySentRequest)
        {
            throw new BadExceptionError("Connection request already exists, cannot send again");
        }

        boolean alreadyConnected= personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected)
        {
            throw new BadExceptionError("CAlready connected users, cannot add connection request");
        }

        log.info("Successfully sent the connection request");
        personRepository.addConnectionRequest(senderId,receiverId);
        ConnectionRequestCreatedEvent event=ConnectionRequestCreatedEvent.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .build();

        connectionRequestCreatedEventKafkaTemplate.send("connection_request_topic",event);


    }

    public void acceptConnectionRequest(Long senderId)
    {
        Long receiverId=UserContextHolder.getCurrentUserId();
        log.info("Accepting connection request with senderId: {}, receiverId:{}",senderId,receiverId);

        if(senderId.equals(receiverId))
        {
            throw new BadExceptionError("Both sender and receiver are same");
        }

        boolean alreadyConnected= personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected)
        {
            throw new BadExceptionError("Already connected users, cannot accept connection request again");
        }

        boolean alreadySentRequest=personRepository.connectionRequestExists(senderId,receiverId);
        if(!alreadySentRequest)
        {
            throw new BadExceptionError("Connection request does not exists, cannot accept without request");
        }


        personRepository.acceptConnectionRequest(senderId,receiverId);
        ConnectionRequestAcceptedEvent event=ConnectionRequestAcceptedEvent.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .build();

        connectionRequestAcceptedEventKafkaTemplate.send("connection_accepted_topic",event);
        log.info("Successfully accepted connection request");

    }

    public void rejectConnectionRequest(Long senderId)
    {
        Long receiverId=UserContextHolder.getCurrentUserId();
        log.info("Rejecting connection request with senderId: {}, receiverId:{}",senderId,receiverId);

        if(senderId.equals(receiverId))
        {
            throw new BadExceptionError("Both sender and receiver are same");
        }

        boolean alreadySentRequest=personRepository.connectionRequestExists(senderId,receiverId);
        if(!alreadySentRequest)
        {
            throw new BadExceptionError("Connection request does not exists, cannot reject without request");
        }

        personRepository.rejectConnectionRequest(senderId,receiverId);
        log.info("Successfully rejected connection request");
    }

}
