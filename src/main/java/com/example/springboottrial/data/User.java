package com.example.springboottrial.data;

public class User {
    private long id;
    private String userid;
    private String displayName;
    private String passwordHash;

    // MEMO: ORMにはデフォルトコンストラクタが必要，setterも必要
    public User() {
    }

    public User(String userid, String displayName, String passwordHash) {
        this.userid = userid;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
