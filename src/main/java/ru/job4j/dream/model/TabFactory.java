package ru.job4j.dream.model;

import java.util.Date;

interface TabFactory<T> {
    T create(int id, String name, String description, Date created, int photo);
}
