package ru.dkalchenko.teatime.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.dkalchenko.teatime.model.Person;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class PersonRepositoryJdbc implements PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL = "SELECT * FROM persons";
    private static final String FIND_BY_ID = "SELECT * FROM persons WHERE id = ?";
    private static final String CREATE = "INSERT INTO persons (first_name, last_name, email) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE persons SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM persons WHERE id = ?";

    private final RowMapper<Person> personRowMapper = (resultSet, rowNum) -> {
        Person person = new Person();
        person.setId(BigInteger.valueOf(resultSet.getInt("id")));
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setEmail(resultSet.getString("email"));
        return person;
    };

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query(FIND_ALL, personRowMapper);
    }

    @Override
    public Optional<Person> findById(BigInteger id) {
        List<Person> result = jdbcTemplate.query(FIND_BY_ID, personRowMapper, id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    @Override
    public Person save(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getEmail());
            return preparedStatement;
        }, keyHolder);
        person.setId((BigInteger) (Objects.requireNonNull(keyHolder.getKeys()).get("id")));
        return person;
    }

    @Override
    public Person update(Person person) {
        jdbcTemplate.update(UPDATE, person.getFirstName(), person.getLastName(), person.getEmail(), person.getId());
        return person;
    }

    @Override
    public void deleteById(BigInteger id) {
        jdbcTemplate.update(DELETE, id);
    }
}
