CREATE TABLE IF NOT EXISTS users (
      id                    INTEGER             NOT NULL AUTO_INCREMENT,
      name                  VARCHAR(255)        NOT NULL,
      password_hash         VARCHAR(100),
      PRIMARY KEY (id)
);
