package com.example.springboottrial.configurations;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        if (authException instanceof UsernameNotFoundException
                || authException instanceof BadCredentialsException) {
            response.addHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else {
            response.addHeader("WWW-Authenticate", "Bearer");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
