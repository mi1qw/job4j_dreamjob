package ru.job4j.dream.model;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCand(int id);

    ImgFile findImgCand(int id);

    ImgFile findImgPost(int id);

    int saveImgCand(String photo, Candidate candidate);

    int saveImgPost(String photo, Post post);

    Map<Integer, String> findAllImg(Type type);

    boolean deleteByIdCand(int id);

    boolean deleteByIdPost(int id);

    boolean deleteImgCand(int id);

    boolean deleteImgPost(int id);

    void cleanUp(Path path);

    User findByEmail(String email);

    void saveUser(User user);

    boolean deleteUser(User user);

    List<String> findAllCities();

    String findByIdCity(int cityId);
}
