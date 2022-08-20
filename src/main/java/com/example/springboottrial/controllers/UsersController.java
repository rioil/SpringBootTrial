package com.example.springboottrial.controllers;

import com.example.springboottrial.services.UserService;
import com.example.springboottrial.data.CreatedUser;
import com.example.springboottrial.data.NewUser;
import com.example.springboottrial.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public CreatedUser createUser(@RequestBody NewUser data) {
        // パスワードのハッシュ値を生成
        var encoder = new BCryptPasswordEncoder();
        var passwordHash = encoder.encode(data.getPassword());

        // ユーザーを追加
        userService.Save(new User(data.getUserid(), data.getDisplayName(), passwordHash));

        return new CreatedUser(data.getUserid(), data.getDisplayName());
    }
}
