package ru.job4j.dream.model;

interface TabFactory<T> {
    T create(int id, String name, String description, java.util.Date created);
}
