package ru.job4j.dream.model;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

enum Type {
    POST(Post::new, "post", Post.class),
    CANDIDATE(Candidate::new, "candidate", Candidate.class);
    //DIRECT(new Direct()),
    //TOPIC(new Topic());
    private final TabFactory<?> tab;
    private final String name;
    private final Class aclass;

    Type(final TabFactory<?> tab, final String name, final Class aclass) {
        this.tab = tab;
        this.name = name;
        this.aclass = aclass;
    }

    public TabFactory<?> getTab() {
        return this.tab;
    }

    public String getName() {
        return this.name;
    }

    public Class getAclass() {
        return aclass;
    }
}


/**
 * The type Psql store.
 */
public final class PsqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return findAll("post", Post::new);
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return findAll("post", Candidate::new);
    }

    private <T> Collection<T> findAll(final String table, final TabFactory<T> tabFactory) {
        List<T> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("SELECT * FROM %s", table))) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    list.add(tabFactory.create(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getDate("created")
                    ));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(final Post post) {
        createQ(post, Type.POST);
        Type type = Type.POST;
        System.out.println(type.getTab() + " construct");
        System.out.println(type.ordinal());
        System.out.println(type.toString());
        System.out.println(type.getDeclaringClass().getSimpleName());
        System.out.println(type.getClass().getSimpleName());
        System.out.println(type.name());
        System.out.println(type.getName());
        if (post.getId() == 0) {
            //createQ(post, Type.POST);
            create(post);
        } else {
            update(post);
        }
    }

    //@Override
    //public void save(final Candidate candidate) {
    //    if (post.getId() == 0) {
    //        create(post);
    //    } else {
    //        update(post);
    //    }
    //}

    //@Override
    //public void save(final Candidate candidate) {
    //    saveIn(candidate);
    //}

    //public <E> void saveIn(final E tab) {
    //    //Post p = (Post) tab;
    //    if (p.getId() == 0) {
    //        create(post);
    //    } else {
    //        update(post);
    //    }
    //}

    private <T> T createQ(final T tab, final Type t) {
        //Class<T> c = (Class<T>) tab;
        //Post c= (Post) tab;

        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("INSERT INTO %s VALUES (DEFAULT,?,?,?)", t.getName()),
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            //            T m = (Post) tab.;
            //t.getTab().getClass()

            Class<T> cc = t.getAclass();
            T m = cc.cast(tab);

            System.out.println(cc.cast(tab) + " !!!!!!!!!!!!!!");
            //cc.cast(tab);
            //T m = tab;
            //Class<?> nn = () tab;
            //m.toString()
            Class<T> persistentClass = (Class<T>)
                    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            //Class<T> persistentClass = (Class<T>)
            //        ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            //    ps.setString(1, tab.getName());
            //    ps.setString(2, tab.getDescription());
            //    ps.setDate(3, new Date(tab.getCreated().getTime()));
            //    ps.execute();
            //    try (ResultSet id = ps.getGeneratedKeys()) {
            //        if (id.next()) {
            //            tab.setId(id.getInt(1));
            //        }
            //    }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return post;
        return null;
    }

    private Post create(final Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post VALUES (DEFAULT,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setDate(3, new Date(post.getCreated().getTime()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void update(final Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name =?, description =?, created =?"
                             + " WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setDate(3, new Date(post.getCreated().getTime()));
            ps.setInt(4, post.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post findById(final int id) {
        ResultSet rs;
        Post item = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post  WHERE id = ?")) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item = new Post(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("created")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
