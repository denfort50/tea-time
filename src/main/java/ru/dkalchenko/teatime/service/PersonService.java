package ru.dkalchenko.teatime.service;

import ru.dkalchenko.teatime.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();

    Optional<Person> findById(String id);

    Optional<Person> findByEmail(String email);

    Person save(Person person);

    Person update(Person person, String id);

    void deleteById(String id);
}
