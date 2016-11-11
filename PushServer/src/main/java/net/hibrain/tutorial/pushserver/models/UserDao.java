package net.hibrain.tutorial.pushserver.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by saltfactory on 12/11/2016.
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  users(id INTEGER PRIMARY KEY AUTOINCREMENT, username varchar(255) UNIQUE , token varchar(255))");

    }

    public int saveUser(User user) {
        return jdbcTemplate.update("insert or replace into users (username, token) values (?, ?)", new Object[]{user.getUsername(), user.getToken()});

    }

    public User getUserByUsername(String username) {
        return (User) jdbcTemplate.queryForObject(
                "select username, token from users where username = ?",
                new Object[]{ username }, new UserRowMapper());


    }

}


class UserRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setToken(rs.getString("token"));
        user.setUsername(rs.getString("username"));
        return user;
    }

}