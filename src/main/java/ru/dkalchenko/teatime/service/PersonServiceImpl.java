package ru.dkalchenko.teatime.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dkalchenko.teatime.exception.PersonByEmailNotFoundException;
import ru.dkalchenko.teatime.model.Person;
import ru.dkalchenko.teatime.repository.PersonRepositoryMongo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepositoryMongo personRepository;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(String id) {
        return personRepository.findById(id);
    }

    public Optional<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Person newPerson, String id) {
        return findById(id)
                .map(person -> {
                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setEmail(newPerson.getEmail());
                    return save(person);
                })
                .orElseThrow(() -> new PersonByEmailNotFoundException(newPerson.getEmail()));
    }

    @Override
    public void deleteById(String id) {
        personRepository.deleteById(id);
    }
}
