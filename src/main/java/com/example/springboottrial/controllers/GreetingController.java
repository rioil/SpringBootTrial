package com.example.springboottrial.controllers;

import com.example.springboottrial.data.Greeting;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private final String welcomeTemplate = "Hello, %s!";
    private final String denyTemplate = "Bye, %s.";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    //public Greeting greeting(@RequestParam(name = "name", defaultValue = "World") String name) {
    public Greeting greeting() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof UserDetails)){
            throw new RequestRejectedException("Invalid User.");
        }

        String username = ((UserDetails)principal).getUsername();
        String message = String.format(welcomeTemplate, username);

        // MEMO: オブジェクトを返すと自動的にJSONにシリアライズしてくれる(Spring BootとJackson)
        return new Greeting(counter.incrementAndGet(), message);
    }
}
