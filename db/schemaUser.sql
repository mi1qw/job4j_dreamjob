CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password TEXT
);
ALTER SEQUENCE users_id_seq RESTART WITH 100;
