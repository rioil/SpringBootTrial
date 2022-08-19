CREATE TABLE IF NOT EXISTS users (
    id                    INTEGER             NOT NULL AUTO_INCREMENT,
    userid                VARCHAR(255)        NOT NULL UNIQUE,
    displayName           VARCHAR(255)        NOT NULL,
    password_hash         VARCHAR(100),
    PRIMARY KEY (id)
);
