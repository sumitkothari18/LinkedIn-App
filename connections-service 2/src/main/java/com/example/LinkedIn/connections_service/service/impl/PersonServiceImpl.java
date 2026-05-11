package com.example.LinkedIn.connections_service.service.impl;


import com.example.LinkedIn.connections_service.entity.Person;
import com.example.LinkedIn.connections_service.repository.PersonRepository;
import com.example.LinkedIn.connections_service.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public void createPerson(Long userId,String name)
    {
        Person person=Person.builder()
                .userId(userId)
                .name(name)
                .build();

        personRepository.save(person);

    }
}
