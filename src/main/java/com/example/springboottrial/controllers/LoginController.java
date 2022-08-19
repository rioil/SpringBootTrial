package com.example.springboottrial.controllers;

import com.example.springboottrial.data.LoginCredential;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @PostMapping("/login")
    public void Login(@RequestBody LoginCredential credential, HttpServletResponse response){
        // TODO: JWT生成
        var token = "key1";

        response.setHeader("X-Auth-Token", token);
    }
}
