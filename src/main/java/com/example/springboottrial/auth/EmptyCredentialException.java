package com.example.springboottrial.auth;

import org.springframework.security.core.AuthenticationException;

public class EmptyCredentialException extends AuthenticationException {
    public EmptyCredentialException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmptyCredentialException(String msg) {
        super(msg);
    }
}
