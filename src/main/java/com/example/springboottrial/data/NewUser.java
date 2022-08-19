package com.example.springboottrial.data;

public class NewUser {
    private String userid;
    private String displayName;
    private String password;

    public NewUser(){
    }

    public NewUser(String userid, String displayName, String password){
        this.userid = userid;
        this.displayName = displayName;
        this.password = password;
    }

    public String getUserid(){
        return userid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }
}
