package ru.dkalchenko.teatime.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dkalchenko.teatime.model.Person;

import java.util.Optional;

public interface PersonRepositoryMongo extends MongoRepository<Person, String> {

    Optional<Person> findByEmail(String email);
}
