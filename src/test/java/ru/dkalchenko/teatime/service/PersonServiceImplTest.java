package ru.dkalchenko.teatime.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dkalchenko.teatime.model.Person;
import ru.dkalchenko.teatime.repository.PersonRepository;

@ContextConfiguration(classes = PersonServiceImpl.class)
@ExtendWith(SpringExtension.class)
class PersonServiceImplTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonServiceImpl personServiceImpl;

    @Test
    public void testFindAll() {
        ArrayList<Person> personList = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(personList);
        List<Person> actualFindAllResult = personServiceImpl.findAll();
        assertSame(personList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(personRepository).findAll();
    }

    @Test
    public void testFindById() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findById(any())).thenReturn(ofResult);
        Optional<Person> actualFindByIdResult = personServiceImpl.findById(1L);
        assertSame(ofResult, actualFindByIdResult);
        assertTrue(actualFindByIdResult.isPresent());
        verify(personRepository).findById(any());
    }

    @Test
    public void testSave() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        when(personRepository.save(any())).thenReturn(person);
        Person person1 = new Person();
        person1.setEmail("jane.doe@example.org");
        person1.setFirstName("Jane");
        person1.setId(1L);
        person1.setLastName("Doe");
        assertSame(person, personServiceImpl.save(person1));
        verify(personRepository).save(any());
    }

    @Test
    public void testUpdate() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        when(personRepository.update(any())).thenReturn(person);
        Person person1 = new Person();
        person1.setEmail("jane.doe@example.org");
        person1.setFirstName("Jane");
        person1.setId(1L);
        person1.setLastName("Doe");
        assertSame(person, personServiceImpl.update(person1));
        verify(personRepository).update(any());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(personRepository).deleteById(any());
        personServiceImpl.deleteById(1L);
        verify(personRepository).deleteById(any());
    }
}

