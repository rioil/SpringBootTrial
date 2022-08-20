package com.example.springboottrial.controllers;

import com.example.springboottrial.data.LoginCredential;
import com.example.springboottrial.auth.JwtUtil;
import com.example.springboottrial.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public void Login(@RequestBody LoginCredential credential, HttpServletResponse response) {
        // useridが一致するユーザーを取得
        var user = userService.find(credential.getUserid());

        // ユーザーが見つからなければログイン失敗
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // パスワードを照合
        var encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(credential.getPassword(), user.getPasswordHash())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // JWT生成
        var token = JwtUtil.createToken(credential.getUserid());

        // ヘッダに生成したトークンを設定
        response.setHeader("X-Auth-Token", token);
    }
}
