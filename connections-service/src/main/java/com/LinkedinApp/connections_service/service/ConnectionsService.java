package com.LinkedinApp.connections_service.service;

import com.LinkedinApp.connections_service.entity.Person;

import java.util.List;

public interface ConnectionsService {

    List<Person> getFirstConnections();

    Person getByName(String name);

    List<Person> getSecondConnections(Long userId);
}
