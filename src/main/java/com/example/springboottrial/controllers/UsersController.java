package com.example.springboottrial.controllers;

import com.example.springboottrial.configurations.UserService;
import com.example.springboottrial.data.CreatedUser;
import com.example.springboottrial.data.NewUser;
import com.example.springboottrial.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public CreatedUser createUser(@RequestBody NewUser data) {
        // TODO: ユーザー追加
        var passwordHash = data.getPassword();  // TODO ハッシュ値生成
        userService.Save(new User(data.getUserid(), data.getDisplayName(), passwordHash));

        return new CreatedUser(data.getUserid(), data.getDisplayName());
    }
}
