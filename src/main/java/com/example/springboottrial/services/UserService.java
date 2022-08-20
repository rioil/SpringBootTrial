package com.example.springboottrial.services;

import com.example.springboottrial.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll(){
        String query = "SELECT * FROM users;";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    public void Save(User user){
        // TODO userid重複時のエラー処理
        var param = new BeanPropertySqlParameterSource(user);
        var insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Number key = insert.executeAndReturnKey(param);
        user.setId(key.longValue());
    }

    public boolean exists(String userid){
        String query = "SELECT count(*) AS num FROM users WHERE userid = ?;";

        var count = jdbcTemplate.queryForObject(query, long.class, userid);

        return count == 1;
    }

    public User find(String userid){
        // TODO 見つからなかったときの例外処理
        String query = "SELECT * FROM users WHERE userid = ?;";

        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), userid);
    }
}
