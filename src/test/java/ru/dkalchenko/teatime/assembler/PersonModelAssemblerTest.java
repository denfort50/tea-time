package ru.dkalchenko.teatime.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dkalchenko.teatime.model.Person;

@ContextConfiguration(classes = PersonModelAssembler.class)
@ExtendWith(SpringExtension.class)
class PersonModelAssemblerTest {

    @Autowired
    private PersonModelAssembler personModelAssembler;

    @Test
    public void testToModel() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        EntityModel<Person> actualToModelResult = personModelAssembler.toModel(person);
        assertSame(person, actualToModelResult.getContent());
        assertTrue(actualToModelResult.hasLinks());
        assertEquals(2, actualToModelResult.getLinks().toList().size());
    }

    @Test
    public void testToModel2() {
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
        EntityModel<Person> actualToModelResult = personModelAssembler.toModel(person);
        assertTrue(actualToModelResult.hasLinks());
        assertEquals(2, actualToModelResult.getLinks().toList().size());
        verify(person).getId();
        verify(person).setEmail(any());
        verify(person).setFirstName(any());
        verify(person).setId(any());
        verify(person).setLastName(any());
    }
}

