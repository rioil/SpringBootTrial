package com.example.springboottrial;

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
        var param = new BeanPropertySqlParameterSource(user);
        var insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Number key = insert.executeAndReturnKey(param);
        user.setId(key.longValue());
    }
}
