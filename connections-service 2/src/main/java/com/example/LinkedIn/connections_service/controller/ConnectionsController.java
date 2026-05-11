package com.example.LinkedIn.connections_service.controller;


import com.example.LinkedIn.connections_service.entity.Person;
import com.example.LinkedIn.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections(@PathVariable Long userId)
    {
        List<Person> personList=connectionsService.getFirstConnections(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{userId}/second-degree")
    public ResponseEntity<List<Person>> getSecondConnections(@PathVariable Long userId)
    {
        List<Person> personList=connectionsService.getSecondConnections(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Person> getByName(@PathVariable String name)
    {
        Person person=connectionsService.getByName(name);
        return ResponseEntity.ok(person);
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long userId)
    {
        connectionsService.sendRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long userId)
    {
        connectionsService.acceptConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long userId)
    {
        connectionsService.rejectConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }



}
