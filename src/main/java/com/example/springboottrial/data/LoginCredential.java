package com.example.springboottrial.data;

public class LoginCredential {
    private String userid;
    private String password;

    public LoginCredential() {
    }

    public LoginCredential(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }
}
