package ru.job4j.dream.model;

import java.util.Date;

public class PsqlMain {
    public static void main(final String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(
                8,
                "Java Job000000000",
                "Java Java Java!00000",
                new Date()));

        System.out.println("_________________________________________");
        store.findAllPosts().forEach(System.out::println);

        System.out.println("_________________________________________");
        System.out.println(store.findById(7) + "    findById");

        System.out.println("_________________________________________");
        store.findAllCandidates().forEach(System.out::println);
    }
}
