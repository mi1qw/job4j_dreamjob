package ru.job4j.dream.model;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCand(int id);

    ImgFile findImgCand(int id);

    int saveImgCand(String photo, Candidate candidate);

    Map<Integer, String> findAllImg(Type type);

    boolean deleteByIdCand(int id);

    boolean deleteImgCand(int id);

    void cleanUp(Path path);

    User findByEmail(String email);

    void saveUser(User user);

    boolean deleteUser(User user);
}
