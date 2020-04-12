package ru.itis.onlineShop.repositories.jdbcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.models.PersonRole;
import ru.itis.onlineShop.repositories.PersonsRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class PersonsRepositoryJdbcImpl implements PersonsRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from person";
    //language=SQL
    private static final String SQL_FIND_BY_EMAIL = "select * from person where email = ?";
    //language=SQL
    private static final String SQL_INSERT = "insert into person(name, email, password, confirmlink, isconfirmed, role) values (?,?,?,?,?,?)";
    //language=SQL
    private static final String SQL_DELETE = "delete from person where email = ?";
    //language=SQL
    private static final String SQL_FIND_BY_CONFIRM_LINK = "select * from person where confirmlink = ?";
    //language=SQL
    private static final String SQL_UPDATE = "update person set isconfirmed = true where email = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PersonsRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Person> userRowMapper = (row, rowNumber) ->
            Person.builder()
                    .name(row.getString("username"))
                    .email(row.getString("email"))
                    .hashPassword(row.getString("password"))
                    .confirmLink(row.getString("confirmlink"))
                    .isConfirmed(row.getBoolean("isconfirmed"))
                    .role(PersonRole.valueOf(row.getString("role")))
                    .build();


    @Override
    public Optional<Person> find(String email) {
        try {
            Person person = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            return Optional.ofNullable(person);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getHashPassword());
            statement.setString(4, person.getConfirmLink());
            statement.setBoolean(5, person.isConfirmed());
            statement.setString(6, person.getRole().toString());
            return statement;
        }, keyHolder);
        person.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(String email) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_DELETE);
            statement.setString(1, email);
            return statement;
        });
    }

    @Override
    public void confirmed(String email) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_UPDATE);
            statement.setString(1, email);
            return statement;
        });
    }

    @Override
    public Optional<Person> findByConfirmLink(String confirmLink) {
        try {
            Person person = jdbcTemplate.queryForObject(SQL_FIND_BY_CONFIRM_LINK, new Object[]{confirmLink}, userRowMapper);
            return Optional.ofNullable(person);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Person> findById(Long id) {
        return Optional.empty();
    }
}
