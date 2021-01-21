create table city
(
    id   serial primary key,
    name TEXT
);
alter table post
    add column city_id int references city (id);
alter table candidate
    add column city_id int references city (id);
ALTER SEQUENCE city_id_seq RESTART WITH 100;