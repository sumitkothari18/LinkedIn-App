package com.example.LinkedIn.connections_service.service;



import com.example.LinkedIn.connections_service.entity.Person;

import java.util.List;

public interface ConnectionsService {

    List<Person> getFirstConnections(Long userId);

    Person getByName(String name);

    List<Person> getSecondConnections(Long userId);

    void sendRequest(Long userId);

    public void acceptConnectionRequest(Long senderId);

    public void rejectConnectionRequest(Long senderId);
}
