create table photopost
(
    id   serial primary key,
    name TEXT
);
alter table post
    add column photo_id int references photopost (id);
ALTER SEQUENCE photopost_id_seq RESTART WITH 100;