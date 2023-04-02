package io.ylab.intensive.lesson05.eventsourcing.repository;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.util.List;

public interface Repository {
    void deletePerson(Long personId);

    void savePerson(String person);

    Person findPerson(Long personId);

    List<Person> findAll();
}
