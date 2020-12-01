package ru.job4j.dream.model;

import java.util.Collection;

interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    //void save(Candidate candidate);

    Post findById(int id);

    //Candidate findById(int id);
}
