package ru.dkalchenko.teatime.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dkalchenko.teatime.model.Person;

@ContextConfiguration(classes = PersonRepositoryJdbc.class)
@ExtendWith(SpringExtension.class)
class PersonRepositoryJdbcTest {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PersonRepositoryJdbc personRepositoryJdbc;

    @Test
    public void testFindAll() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (RowMapper<Object>) any())).thenReturn(objectList);
        List<Person> actualFindAllResult = personRepositoryJdbc.findAll();
        assertSame(objectList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object>) any());
    }

    @Test
    public void testFindById() throws DataAccessException {
        when(jdbcTemplate.query(any(), (RowMapper<Object>) any(), any()))
                .thenReturn(new ArrayList<>());
        assertFalse(personRepositoryJdbc.findById(1L).isPresent());
        verify(jdbcTemplate).query(any(), (RowMapper<Object>) any(), any());
    }

    @Test
    public void testUpdate() throws DataAccessException {
        when(jdbcTemplate.update(any(), (Object[]) any())).thenReturn(1);
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        assertSame(person, personRepositoryJdbc.update(person));
        verify(jdbcTemplate).update(any(), (Object[]) any());
    }

    @Test
    public void testDeleteById() throws DataAccessException {
        when(jdbcTemplate.update(any(), (Object[]) any())).thenReturn(1);
        personRepositoryJdbc.deleteById(1L);
        verify(jdbcTemplate).update(any(), (Object[]) any());
    }
}

