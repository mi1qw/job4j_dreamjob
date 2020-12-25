package ru.job4j.dream.model;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.*;
import java.util.*;

/**
 * The type Psql store.
 */
public final class PsqlStore implements Store {
    public static final Logger LOGGER = LoggerFactory.getLogger(PsqlStore.class);
    public static final String LN = System.lineSeparator();
    private static final Store INST = new PsqlStore();
    private final BasicDataSource pool = new BasicDataSource();
    private static String noimage;
    public static final String IMAGES = "images";

    public PsqlStore() {
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
        noimage = initImages();
        initUsers();
    }

    public static Store instOf() {
        return INST;
    }

    public static String getNoimage() {
        return noimage;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return (Collection<Post>) (Object) findAll(Type.POST);
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return (Collection<Candidate>) (Object) findAll(Type.CANDIDATE);
    }

    private <T> Collection<T> findAll(final Type type) {
        //(type.getAclass())
        List<T> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("SELECT * FROM %s", type.getName()))) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    list.add((T) type.getTab().create(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getDate("created"),
                            it.getInt("photo_id")
                    ));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void save(final Post post) {
        Object[] o = {
                post.getId(),
                post.getName(),
                post.getDescription(),
                post.getCreated(),
                post.getPhotoId()
        };
        if (post.getId() == 0) {
            post.setId(create(o, Type.POST));
        } else {
            update(o, Type.POST);
        }
    }

    @Override
    public void save(final Candidate candidate) {
        Object[] o = {
                candidate.getId(),
                candidate.getName(),
                candidate.getDescription(),
                candidate.getCreated(),
                candidate.getPhotoId()
        };
        if (candidate.getId() == 0) {
            candidate.setId(create(o, Type.CANDIDATE));
        } else {
            update(o, Type.CANDIDATE);
        }
    }

    private int create(final Object[] o, final Type t) {
        int oId = 0;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("INSERT INTO %s VALUES (DEFAULT,?,?,?,?)", t.getName()),
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, (String) o[1]);
            ps.setString(2, (String) o[2]);
            ps.setDate(3, new Date(((java.util.Date) o[3]).getTime()));
            ps.setInt(4, (Integer) o[4]);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    oId = id.getInt(1);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return oId;
    }

    private void update(final Object[] o, final Type t) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("UPDATE %s SET name =?, description =?, created =?, photo_id "
                             + "=? WHERE id = ?", t.getName()))) {
            ps.setString(1, (String) o[1]);
            ps.setString(2, (String) o[2]);
            ps.setDate(3, new Date(((java.util.Date) o[3]).getTime()));
            ps.setInt(4, (Integer) o[4]);
            ps.setInt(5, (Integer) o[0]);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public Post findByIdPost(final int id) {
        return findById(id, Type.POST);
    }

    @Override
    public Candidate findByIdCand(final int id) {
        return findById(id, Type.CANDIDATE);
    }

    private <T> T findById(final int id, final Type type) {
        ResultSet rs;
        T item = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("SELECT * FROM %s  WHERE id = ?", type.getName()))) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item = (T) type.getTab().create(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("created"),
                        rs.getInt("photo_id")
                );
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return item;
    }

    /**
     * find Photo By Id.
     *
     * @param id id
     * @return res
     */
    @Override
    public ImgFile findImgCand(final int id) {
        return findPhoto(id, Type.CANDIDATE);
    }

    /**
     * find Photo By Id.
     *
     * @param id id
     * @return res
     */
    @Override
    public ImgFile findImgPost(final int id) {
        return findPhoto(id, Type.POST);
    }

    /**
     * find Photo By Id.
     *
     * @param id   id
     * @param type type
     * @return res
     */
    private ImgFile findPhoto(final int id, final Type type) {
        ResultSet rs;
        ImgFile img = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("SELECT * FROM %s  WHERE id = ?", type.getImgname()))) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                img = new ImgFile(id, rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return img;
    }

    /**
     * saveImg.
     * If their is new user or without photo (PhotoId=1) then, then saveImg/add new photo to DB
     *
     * @param photo name of photo
     * @return photoId
     */
    @Override
    public int saveImgPost(final String photo, final Post post) {
        int pId = post.getPhotoId();
        if (pId == 1) {
            return saveImg(photo, Type.POST);
        } else {
            return updateImg(photo, pId, Type.POST);
        }
    }

    /**
     * saveImg.
     * If their is new user or without photo (PhotoId=1) then, then saveImg/add new photo to DB
     *
     * @param photo name of photo
     * @return photoId
     */
    @Override
    public int saveImgCand(final String photo, final Candidate candidate) {
        int pId = candidate.getPhotoId();
        if (pId == 1) {
            return saveImg(photo, Type.CANDIDATE);
        } else {
            return updateImg(photo, pId, Type.CANDIDATE);
        }
    }

    private int saveImg(final String photo, final Type type) {
        int oId = 0;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("INSERT INTO %s VALUES (DEFAULT,?)", type.getImgname()),
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, photo);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    oId = id.getInt(1);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return oId;
    }

    private int updateImg(final String name, final int id, final Type type) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("UPDATE %s SET name =? WHERE id = ?", type.getImgname()))) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return id;
    }

    public Map<Integer, String> findAllImg(final Type type) {
        Map<Integer, String> map = new HashMap<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("SELECT * FROM %s", type.getImgname()))) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    map.put(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * Выполнить как-то запрос/команду
     *
     * @param query query + строка для вывода в лог
     */
    private void doQuery(final String... query) {
        try (Connection cn = pool.getConnection(); Statement st = cn.createStatement()) {
            boolean isResult = st.execute(query[0]);
            if (isResult) {
                StringBuilder sb = new StringBuilder();
                try (ResultSet resalt = st.getResultSet()) {
                    while (resalt.next()) {
                        for (int n = 1; n <= resalt.getMetaData().getColumnCount(); ++n) {
                            sb.append(resalt.getObject(n));
                            sb.append(" ");
                        }
                        sb.append(LN);
                    }
                }
                String str = query.length > 1 ? query[1].concat(LN) : "";
                if (query.length > 1) {
                    LOGGER.info("Query {}{}", str, sb);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public boolean deleteByIdCand(final int id) {
        return delete(id, Type.CANDIDATE.getName());
    }

    public boolean deleteByIdPost(final int id) {
        return delete(id, Type.POST.getName());
    }

    public boolean deleteImgCand(final int id) {
        return delete(id, Type.CANDIDATE.getImgname());
    }

    public boolean deleteImgPost(final int id) {
        return delete(id, Type.POST.getImgname());
    }

    private boolean delete(final int id, final String name) {
        String tableQuery = String.format("DELETE FROM %s WHERE id = ?", name);
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement(tableQuery)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    public String initImages() {
        doQuery("INSERT INTO photo VALUES(1,'noimages.png') on conflict DO NOTHING;");
        doQuery("INSERT INTO photopost VALUES(1,'noimages.png') on conflict DO NOTHING;");
        Path path = Path.of(IMAGES, "noimages.png");
        createNoimagFile(path);
        return "noimages.png";
    }

    public void initUsers() {
        doQuery("INSERT INTO users VALUES(1,'Admin','root@local','root') on conflict DO NOTHING;");
    }

    private void createNoimagFile(final Path path) {
        URL pth = Thread.currentThread().getContextClassLoader()
                .getResource("noimages.png");
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path.getParent());
                assert pth != null;
                Files.copy(Paths.get(pth.toURI()), path);
            } catch (IOException | URISyntaxException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Clean up.
     * Delete file at the specified path
     *
     * @param path the path
     */
    @Override
    public void cleanUp(final Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void saveUser(final User user) {
        if (user.getId() == 0) {
            user.setId(createUser(user));
        } else {
            updateUser(user);
        }
    }

    private int createUser(final User user) {
        int id = 0;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     String.format("INSERT INTO %s VALUES (DEFAULT,?,?,?)", "users"),
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return id;
    }

    private void updateUser(final User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE users SET name =?, email =?, password =? WHERE id = ?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public User findByEmail(final String email) {
        ResultSet rs;
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = ?")) {
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public boolean deleteUser(final User user) {
        String tableQuery = String.format("DELETE FROM %s WHERE id = ?", "users");
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement(tableQuery)) {
            st.setInt(1, user.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
