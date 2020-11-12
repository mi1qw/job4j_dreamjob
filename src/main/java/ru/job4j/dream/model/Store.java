package ru.job4j.dream.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Store {
    private static final Store INST = new Store();
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job",
                "We are looking for many juniors", "12/11/2020"));
        posts.put(2, new Post(2, "Middle Java Job",
                "Need a middle team", "11/11/2020"));
        posts.put(3, new Post(3, "Senior Java Job",
                "One Senior required", "10/11/2020"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
