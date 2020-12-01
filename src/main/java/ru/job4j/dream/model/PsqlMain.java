package ru.job4j.dream.model;

import java.util.Date;

public class PsqlMain {
    public static void main(final String[] args) {
        Store store = PsqlStore.instOf();
        System.out.println("_______________save_________________________________");
        store.save(new Post(
                8,
                "Java Job000000000",
                "Java Java Java!00000",
                new Date()));


        System.out.println();

        System.out.println("_______________findAllPosts__________________________");
        store.findAllPosts().forEach(System.out::println);
        System.out.println();

        System.out.println("_______________findById(7)____________________________");
        System.out.println(store.findById(7));
        System.out.println();

        System.out.println("_______________findAllCandidates______________________");
        store.findAllCandidates().forEach(System.out::println);
    }
}
