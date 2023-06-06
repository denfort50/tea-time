package ru.dkalchenko.teatime.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.dkalchenko.teatime.assembler.PersonModelAssembler;
import ru.dkalchenko.teatime.exception.PersonNotFoundException;
import ru.dkalchenko.teatime.model.Person;
import ru.dkalchenko.teatime.repository.PersonRepositoryJdbc;
import ru.dkalchenko.teatime.service.PersonService;
import ru.dkalchenko.teatime.service.PersonServiceImpl;

@ContextConfiguration(classes = PersonController.class)
@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @MockBean
    private PersonService personService;

    @Test
    public void testAll() throws Exception {
        when(personService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons");
        MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/persons\"}],\"content\":[]}"));
    }

    @Test
    public void testOne() throws DataAccessException {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        when(jdbcTemplate.query(any(), (RowMapper<Object>) any(), any()))
                .thenReturn(new ArrayList<>());
        PersonServiceImpl personService = new PersonServiceImpl(new PersonRepositoryJdbc(jdbcTemplate));
        assertThrows(PersonNotFoundException.class,
                () -> (new PersonController(personService, new PersonModelAssembler())).one(1L));
        verify(jdbcTemplate).query(any(), (RowMapper<Object>) any(), any());
    }

    @Test
    public void testOne3() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        PersonRepositoryJdbc personRepositoryJdbc = mock(PersonRepositoryJdbc.class);
        when(personRepositoryJdbc.findById((Long) any())).thenReturn(Optional.of(person));
        PersonServiceImpl personService = new PersonServiceImpl(personRepositoryJdbc);
        PersonController personController = new PersonController(personService, new PersonModelAssembler());
        EntityModel<Person> actualOneResult = personController.one(1L);
        assertSame(person, actualOneResult.getContent());
        assertTrue(actualOneResult.hasLinks());
        assertEquals(2, actualOneResult.getLinks().toList().size());
        verify(personRepositoryJdbc).findById((Long) any());
        assertEquals(1, personController.all().getLinks().toList().size());
    }

    @Test
    public void testOne4() {
        Person person = mock(Person.class);
        when(person.getId()).thenReturn(1L);
        doNothing().when(person).setEmail(any());
        doNothing().when(person).setFirstName(any());
        doNothing().when(person).setId(any());
        doNothing().when(person).setLastName(any());
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        PersonRepositoryJdbc personRepositoryJdbc = mock(PersonRepositoryJdbc.class);
        when(personRepositoryJdbc.findById(any())).thenReturn(Optional.of(person));
        PersonServiceImpl personService = new PersonServiceImpl(personRepositoryJdbc);
        PersonController personController = new PersonController(personService, new PersonModelAssembler());
        EntityModel<Person> actualOneResult = personController.one(1L);
        assertTrue(actualOneResult.hasLinks());
        assertEquals(2, actualOneResult.getLinks().toList().size());
        verify(personRepositoryJdbc).findById(any());
        verify(person).getId();
        verify(person).setEmail(any());
        verify(person).setFirstName(any());
        verify(person).setId(any());
        verify(person).setLastName(any());
        assertEquals(1, personController.all().getLinks().toList().size());
    }

    @Test
    public void testOne6() {
        Person person = mock(Person.class);
        when(person.getId()).thenReturn(1L);
        doNothing().when(person).setEmail(any());
        doNothing().when(person).setFirstName(any());
        doNothing().when(person).setId(any());
        doNothing().when(person).setLastName(any());
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        PersonRepositoryJdbc personRepositoryJdbc = mock(PersonRepositoryJdbc.class);
        when(personRepositoryJdbc.findById(any())).thenReturn(Optional.of(person));
        PersonServiceImpl personService = new PersonServiceImpl(personRepositoryJdbc);
        PersonModelAssembler personModelAssembler = mock(PersonModelAssembler.class);
        when(personModelAssembler.toModel(any())).thenReturn((EntityModel<Person>) mock(EntityModel.class));
        PersonController personController = new PersonController(personService, personModelAssembler);
        personController.one(1L);
        verify(personRepositoryJdbc).findById(any());
        verify(person).setEmail(any());
        verify(person).setFirstName(any());
        verify(person).setId(any());
        verify(person).setLastName(any());
        verify(personModelAssembler).toModel(any());
        assertEquals(1, personController.all().getLinks().toList().size());
    }

    @Test
    public void testReplacePerson2() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        Person person1 = new Person();
        person1.setEmail("jane.doe@example.org");
        person1.setFirstName("Jane");
        person1.setId(1L);
        person1.setLastName("Doe");
        Person person2 = new Person();
        person2.setEmail("jane.doe@example.org");
        person2.setFirstName("Jane");
        person2.setId(1L);
        person2.setLastName("Doe");
        PersonRepositoryJdbc personRepositoryJdbc = mock(PersonRepositoryJdbc.class);
        when(personRepositoryJdbc.update(any())).thenReturn(person2);
        when(personRepositoryJdbc.findById(any())).thenReturn(ofResult);
        when(personRepositoryJdbc.save(any())).thenReturn(person1);
        PersonServiceImpl personService = new PersonServiceImpl(personRepositoryJdbc);
        PersonController personController = new PersonController(personService, new PersonModelAssembler());
        Person person3 = new Person();
        person3.setEmail("jane.doe@example.org");
        person3.setFirstName("Jane");
        person3.setId(1L);
        person3.setLastName("Doe");
        ResponseEntity<?> actualReplacePersonResult = personController.replacePerson(person3, 1L);
        assertTrue(actualReplacePersonResult.hasBody());
        assertEquals(1, actualReplacePersonResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualReplacePersonResult.getStatusCode());
        assertSame(person2, ((EntityModel<Object>) actualReplacePersonResult.getBody()).getContent());
        assertEquals(2, ((EntityModel<Object>) actualReplacePersonResult.getBody()).getLinks().toList().size());
        assertTrue(((EntityModel<Object>) actualReplacePersonResult.getBody()).hasLinks());
        verify(personRepositoryJdbc).findById(any());
        verify(personRepositoryJdbc).update(any());
        assertEquals(1, personController.all().getLinks().toList().size());
    }

    @Test
    public void testReplacePerson4() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        Person person1 = new Person();
        person1.setEmail("jane.doe@example.org");
        person1.setFirstName("Jane");
        person1.setId(1L);
        person1.setLastName("Doe");
        PersonRepositoryJdbc personRepositoryJdbc = mock(PersonRepositoryJdbc.class);
        when(personRepositoryJdbc.update(any())).thenReturn(person1);
        when(personRepositoryJdbc.findById(any())).thenReturn(Optional.empty());
        when(personRepositoryJdbc.save(any())).thenReturn(person);
        PersonServiceImpl personService = new PersonServiceImpl(personRepositoryJdbc);
        PersonController personController = new PersonController(personService, new PersonModelAssembler());
        Person person2 = new Person();
        person2.setEmail("jane.doe@example.org");
        person2.setFirstName("Jane");
        person2.setId(1L);
        person2.setLastName("Doe");
        ResponseEntity<?> actualReplacePersonResult = personController.replacePerson(person2, 1L);
        assertTrue(actualReplacePersonResult.hasBody());
        assertEquals(1, actualReplacePersonResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualReplacePersonResult.getStatusCode());
        assertSame(person, ((EntityModel<Object>) actualReplacePersonResult.getBody()).getContent());
        assertEquals(2, ((EntityModel<Object>) actualReplacePersonResult.getBody()).getLinks().toList().size());
        assertTrue(((EntityModel<Object>) actualReplacePersonResult.getBody()).hasLinks());
        verify(personRepositoryJdbc).findById(any());
        verify(personRepositoryJdbc).save(any());
        assertEquals(1L, person2.getId().longValue());
        assertEquals(1, personController.all().getLinks().toList().size());
    }

    @Test
    public void testReplacePerson7() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        Person person1 = new Person();
        person1.setEmail("jane.doe@example.org");
        person1.setFirstName("Jane");
        person1.setId(1L);
        person1.setLastName("Doe");
        Person person2 = new Person();
        person2.setEmail("jane.doe@example.org");
        person2.setFirstName("Jane");
        person2.setId(1L);
        person2.setLastName("Doe");
        PersonService personService = mock(PersonService.class);
        when(personService.update((Person) any())).thenReturn(person2);
        when(personService.findById((Long) any())).thenReturn(ofResult);
        when(personService.save((Person) any())).thenReturn(person1);
        EntityModel<Person> entityModel = (EntityModel<Person>) mock(EntityModel.class);
        when(entityModel.getRequiredLink((LinkRelation) any())).thenReturn(Link.of("Href"));
        PersonModelAssembler personModelAssembler = mock(PersonModelAssembler.class);
        when(personModelAssembler.toModel((Person) any())).thenReturn(entityModel);
        PersonController personController = new PersonController(personService, personModelAssembler);
        Person person3 = new Person();
        person3.setEmail("jane.doe@example.org");
        person3.setFirstName("Jane");
        person3.setId(1L);
        person3.setLastName("Doe");
        ResponseEntity<?> actualReplacePersonResult = personController.replacePerson(person3, 1L);
        assertTrue(actualReplacePersonResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualReplacePersonResult.getStatusCode());
        assertEquals(1, actualReplacePersonResult.getHeaders().size());
        verify(personService).findById((Long) any());
        verify(personService).update((Person) any());
        verify(personModelAssembler).toModel((Person) any());
        verify(entityModel).getRequiredLink((LinkRelation) any());
        assertEquals(1, personController.all().getLinks().toList().size());
    }

    @Test
    public void testReplacePerson8() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        Person person1 = new Person();
        person1.setEmail("jane.doe@example.org");
        person1.setFirstName("Jane");
        person1.setId(1L);
        person1.setLastName("Doe");
        Person person2 = new Person();
        person2.setEmail("jane.doe@example.org");
        person2.setFirstName("Jane");
        person2.setId(1L);
        person2.setLastName("Doe");
        PersonService personService = mock(PersonService.class);
        when(personService.update((Person) any())).thenReturn(person2);
        when(personService.findById((Long) any())).thenReturn(ofResult);
        when(personService.save((Person) any())).thenReturn(person1);
        EntityModel<Person> entityModel = (EntityModel<Person>) mock(EntityModel.class);
        when(entityModel.getRequiredLink((LinkRelation) any())).thenThrow(new PersonNotFoundException(1L));
        PersonModelAssembler personModelAssembler = mock(PersonModelAssembler.class);
        when(personModelAssembler.toModel((Person) any())).thenReturn(entityModel);
        PersonController personController = new PersonController(personService, personModelAssembler);
        Person person3 = new Person();
        person3.setEmail("jane.doe@example.org");
        person3.setFirstName("Jane");
        person3.setId(1L);
        person3.setLastName("Doe");
        assertThrows(PersonNotFoundException.class, () -> personController.replacePerson(person3, 1L));
        verify(personService).findById((Long) any());
        verify(personService).update((Person) any());
        verify(personModelAssembler).toModel((Person) any());
        verify(entityModel).getRequiredLink((LinkRelation) any());
    }

    @Test
    public void testDeletePerson() throws Exception {
        doNothing().when(personService).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/persons/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testNewPerson() throws Exception {
        when(personService.findAll()).thenReturn(new ArrayList<>());
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(person);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/persons\"}],\"content\":[]}"));
    }
}

