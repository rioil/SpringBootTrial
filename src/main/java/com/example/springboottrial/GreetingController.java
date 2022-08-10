package com.example.springboottrial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private final String welcomeTemplate = "Hello, %s!";
    private final String denyTemplate = "Bye, %s.";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private UserService userService;

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(name = "name", defaultValue = "World") String name) {
        String message;
        // MEMO: Objects.equalsを使うと両方nullでも大丈夫
        if (userService.findAll().stream().anyMatch(user -> Objects.equals(user.getName(), name))) {
            message = String.format(welcomeTemplate, name);
        }
        else {
            message = String.format(denyTemplate, name);
        }

        // MEMO: オブジェクトを返すと自動的にJSONにシリアライズしてくれる(Spring BootとJackson)
        return new Greeting(counter.incrementAndGet(), message);
    }
}
