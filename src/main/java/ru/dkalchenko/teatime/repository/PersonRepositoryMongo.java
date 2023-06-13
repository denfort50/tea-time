package ru.dkalchenko.teatime.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dkalchenko.teatime.model.Person;

import java.math.BigInteger;

public interface PersonRepositoryMongo extends MongoRepository<Person, BigInteger>{

}
