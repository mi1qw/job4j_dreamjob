package ru.job4j.dream.model;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCand(int id);
}
