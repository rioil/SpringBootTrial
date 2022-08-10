package com.example.springboottrial;

public class User {
    private long id;
    private String name;
    private String passwordHash;

    // MEMO: ORMにはデフォルトコンストラクタが必要，setterも必要
    public User() {
    }

    public User(long id, String name, String passwordHash) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
