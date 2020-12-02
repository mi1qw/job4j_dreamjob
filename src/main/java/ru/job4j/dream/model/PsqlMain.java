package ru.job4j.dream.model;

import java.util.Date;

public class PsqlMain {
    public static void main(final String[] args) {
        Store store = PsqlStore.instOf();

        //System.out.println("_______________save_________________________________");
        //store.save(new Post(
        //        0,
        //        "Java Job000000000",
        //        "Java Java Java!00000",
        //        new Date()));
        //
        //store.save(new Candidate(
        //        0,
        //        "Java Job000000000",
        //        "Java Java Java!00000",
        //        new Date()));

        store.save(new Post(
                3,
                "C++",
                "Java Java Java!00000",
                new Date()));
        store.save(new Candidate(
                3,
                "C++",
                "Java Java Java!00000",
                new Date()));
        System.out.println();

        System.out.println("_______________findAllPosts__________________________");
        store.findAllPosts().forEach(System.out::println);
        System.out.println();

        System.out.println("_______________findById(7)____________________________");
        System.out.println(store.findPostById(7));
        System.out.println();

        System.out.println("_______________findAllCandidates______________________");
        store.findAllCandidates().forEach(System.out::println);

        System.out.println("_______________findById_______________________________");
        Post pp = store.findPostById(3);
        Candidate cc = store.findCandById(1);
        System.out.println(pp);
        System.out.println(cc);
        //System.out.println(store.findPostById(3));
    }
}
