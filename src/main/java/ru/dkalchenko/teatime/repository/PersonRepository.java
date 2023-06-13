package ru.dkalchenko.teatime.repository;

import ru.dkalchenko.teatime.model.Person;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> findAll();

    Optional<Person> findById(BigInteger id);

    Person save(Person person);

    Person update(Person person);

    void deleteById(BigInteger id);
}
