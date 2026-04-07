package com.LinkedinApp.connections_service.repository;

import com.LinkedinApp.connections_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person,Long> {


    @Query("MATCH (personA:Person {userId: $userId})-[:CONNECTED_TO]-(personB:Person)\n" +
            "RETURN personB;")
    List<Person> getFirstDegreeConnections(Long userId);

    Person getByName(String name);

    @Query("MATCH (p:Person {userId: $userId})-[:CONNECTED_TO*2]->(other:Person)\n" +
            "WHERE other.userId <> $userId\n" +
            "RETURN DISTINCT other;")
    List<Person> getSecondDegreeConnections(Long userId);
}
