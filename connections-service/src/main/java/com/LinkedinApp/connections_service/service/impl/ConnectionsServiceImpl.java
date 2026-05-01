package com.LinkedinApp.connections_service.service.impl;

import com.LinkedinApp.connections_service.auth.UserContextHolder;
import com.LinkedinApp.connections_service.entity.Person;
import com.LinkedinApp.connections_service.repository.PersonRepository;
import com.LinkedinApp.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsServiceImpl implements ConnectionsService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> getFirstConnections() {
        Long userId= UserContextHolder.getCurrentUserId();
        
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
}
