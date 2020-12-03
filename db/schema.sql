CREATE TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     date
);
CREATE TABLE candidate
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     date
);
create table photo
(
    id   serial primary key,
    name TEXT
);

alter table candidate
    add column photo_id int references photo (id);