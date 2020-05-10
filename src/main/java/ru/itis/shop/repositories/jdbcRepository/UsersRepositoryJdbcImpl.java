package ru.itis.shop.repositories.jdbcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.shop.models.User;
import ru.itis.shop.models.UserRole;
import ru.itis.shop.repositories.UsersRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

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

    public UsersRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .name(row.getString("username"))
                    .email(row.getString("email"))
                    .hashPassword(row.getString("password"))
                    .confirmLink(row.getString("confirmlink"))
                    .isConfirmed(row.getBoolean("isconfirmed"))
                    .role(UserRole.valueOf(row.getString("role")))
                    .build();


    @Override
    public Optional<User> find(String email) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getHashPassword());
            statement.setString(4, user.getConfirmLink());
            statement.setBoolean(5, user.isConfirmed());
            statement.setString(6, user.getRole().toString());
            return statement;
        }, keyHolder);
        user.setId((Long) keyHolder.getKey());
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
    public void deleteUserById(Long id) {

    }

    @Override
    public void updatePassword(String email, String password) {

    }

    @Override
    public void updateName(String email, String password) {

    }

    @Override
    public Optional<User> findByConfirmLink(String confirmLink) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_CONFIRM_LINK, new Object[]{confirmLink}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }
}
