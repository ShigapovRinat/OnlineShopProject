package ru.itis.shop.repositories.jdbcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.shop.models.Good;
import ru.itis.shop.models.Order;
import ru.itis.shop.models.User;
import ru.itis.shop.models.UserRole;
import ru.itis.shop.repositories.UsersRepository;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select person.id as user_id, person.email, person.name, " +
            "person.type, person.hashpassword, person..confirmlink, person..isconfirmed, " +
            "\"order\".id, \"order\".create_at, \"order\".good_id, \"order\".quantity_good, \"order\".order_id " +
            "from person left join \"order\" o on person.id = o.person_id";
    //language=SQL
    private static final String SQL_FIND_BY_EMAIL = "select * from person where email = ?";
    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from person where id = ?";
    //language=SQL
    private static final String SQL_INSERT = "insert into person(name, email, password, confirmlink, isconfirmed, role) values (?,?,?,?,?,?)";
    //language=SQL
    private static final String SQL_DELETE = "delete from person where email = ?";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from person where id = ?";
    //language=SQL
    private static final String SQL_FIND_BY_CONFIRM_LINK = "select * from person where confirmlink = ?";
    //language=SQL
    private static final String SQL_UPDATE = "update person set isconfirmed = true where email = ?";
    //language=SQL
    private static final String SQL_UPDATE_PASSWORD = "update person set hashpassword = ? where email = ?";
    //language=SQL
    private static final String SQL_UPDATE_NAME = "update person set name = ? where email = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<Long, User> usersMap = new HashMap<>();

    public UsersRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRowMapper = (row, rowNumber) -> {
        Long id = row.getLong("user_id");

        if (!usersMap.containsKey(id)) {
            User user = User.builder()
                    .id(id)
                    .name(row.getString("name"))
                    .email(row.getString("email"))
                    .hashPassword(row.getString("hashpassword"))
                    .confirmLink(row.getString("confirmlink"))
                    .isConfirmed(row.getBoolean("isconfirmed"))
                    .role(UserRole.valueOf(row.getString("role")))
                    .build();
            usersMap.put(id, user);
        }

        Order order = Order.builder()
                .id(row.getLong("id"))
                .user(usersMap.get(row.getLong("user_id")))
                .createAt(row.getTimestamp("create_at").toLocalDateTime())
                .good(Good.builder().id(row.getLong("good_id")).build())
                .quantityGood(row.getInt("quantity_good"))
                .orderId(row.getString("order_id"))
                .build();

        usersMap.get(id).getOrders().add(order);
        return usersMap.get(id);
    };


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
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_DELETE_BY_ID);
            statement.setLong(1, id);
            return statement;
        });
    }

    @Override
    public void updatePassword(String email, String password) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_UPDATE_PASSWORD);
            statement.setString(1, password);
            statement.setString(2, email);
            return statement;
        });
    }

    @Override
    public void updateName(String email, String name) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_UPDATE_NAME);
            statement.setString(1, name);
            statement.setString(2, email);
            return statement;
        });
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
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
