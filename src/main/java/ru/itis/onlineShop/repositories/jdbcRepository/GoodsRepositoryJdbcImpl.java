package ru.itis.onlineShop.repositories.jdbcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.GoodType;
import ru.itis.onlineShop.repositories.GoodsRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;


public class GoodsRepositoryJdbcImpl implements GoodsRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from good";
    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from good where id = ?";
    //language=SQL
    private static final String SQL_INSERT = "insert into good (title, description, price, type) values (?,?,?,?)";
    //language=SQL
    private static final String SQL_DELETE = "delete from good where title = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GoodsRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Good> userRowMapper = (row, rowNumber) ->
            Good.builder()
                    .title(row.getString("title"))
                    .description(row.getString("description"))
                    .price(row.getInt("price"))
                    .type(GoodType.valueOf(row.getString("type")))
                    .build();


    @Override
    public Optional<Good> find(Long id) {
        try {
            Good good = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(good);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Good> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(Good good) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
            statement.setString(1, good.getTitle());
            statement.setString(2, good.getDescription());
            statement.setInt(3, good.getPrice());
            statement.setString(4, good.getType().toString());
            return statement;
        }, keyHolder);
        good.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            return statement;
        });
    }
}
