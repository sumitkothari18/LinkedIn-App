package com.LinkedinApp.connections_service.controller;

import com.LinkedinApp.connections_service.entity.Person;
import com.LinkedinApp.connections_service.service.ConnectionsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections()
    {
        List<Person> personList=connectionsService.getFirstConnections();
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

}
