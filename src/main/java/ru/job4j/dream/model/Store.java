package ru.job4j.dream.model;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class Store {
    private static final Store INST = new Store();
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job",
                "We are looking for many juniors", "12.11.2020"));
        posts.put(2, new Post(2, "Middle Java Job",
                "Need a middle team", "11.11.2020"));
        posts.put(3, new Post(3, "Senior Java Job",
                "One Senior required", "10.11.2020"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(final Post post) {
        post.setId(POST_ID.incrementAndGet());
        post.setCreated(df.format(new Date()));
        posts.put(post.getId(), post);
    }
}
