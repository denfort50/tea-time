package ru.dkalchenko.teatime.service;

import ru.dkalchenko.teatime.model.Person;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();

    Optional<Person> findById(BigInteger id);

    Person save(Person person);

    Person update(Person person);

    void deleteById(BigInteger id);
}
