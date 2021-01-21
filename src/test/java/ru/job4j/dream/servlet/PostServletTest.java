package ru.job4j.dream.servlet;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.ImgFile;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.PsqlStore;
import ru.job4j.dream.model.Type;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PowerMockRunnerDelegate(JUnit4.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*",
        "org.apache.http.conn.ssl.*", "com.amazonaws.*", "javax.net.ssl.*", "com.sun.*",
        "org.w3c.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({BasicDataSource.class, PsqlStore.class, PostServlet.class})
public class PostServletTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostServletTest.class);
    private static Connection conn;
    private static HttpServletRequest req;
    private static HttpServletResponse resp;
    private static HttpSession ssn;
    private static PostServlet psvt;
    private static PsqlStore psql;

    static {
        try {
            conn = ConnectionRollback.create(ConnectionRollback.init());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Before
    public void before() throws Exception {
        spy(PsqlStore.class);
        psql = spy(new PsqlStore());
        when(PsqlStore.instOf()).thenReturn(psql);
        doNothing().when(psql).cleanUp(any());

        BasicDataSource bds = mock(BasicDataSource.class);
        when(bds.getConnection()).thenReturn(conn);
        Whitebox.setInternalState(psql, "pool", bds);

        spy(PostServlet.class);
        psvt = spy(new PostServlet());
        doReturn("anyImg").when(psvt, "rename", anyString(), anyInt());

        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        ssn = mock(HttpSession.class);
        when(req.getSession()).thenReturn(ssn);
    }

    @AfterClass
    public static void end() throws SQLException {
        conn.close();
    }

    private int addItem(final Post post, final ImgFile oldPhoto, final ImgFile newPhoto,
                        final int cityId, final String... param) {
        when(req.getSession().getAttribute("post")).thenReturn(post);
        when(req.getSession().getAttribute("oldPhoto")).thenReturn(oldPhoto);
        when(req.getSession().getAttribute("photo")).thenReturn(newPhoto);

        when(req.getParameter("name")).thenReturn(param[0]);
        when(req.getParameter("description")).thenReturn(param[1]);
        when(req.getParameter("city")).thenReturn(String.valueOf(cityId));

        psvt.doPost(req, resp);
        return post.getId();
    }

    @Test
    public void a0addNewPostWithoutImgAndDelete() {            // добавить/удалить post без фото
        Post post = new Post(0, "", "", new Date(), 1, 0);
        ImgFile oldPhoto = new ImgFile(1, "imagespost-noimages.png");
        ImgFile newPhoto = new ImgFile(1, "imagespost-noimages.png");

        int id = addItem(post, oldPhoto, newPhoto, 1, "name", "description");
        assertTrue(post.getName().equals("name") & post.getDescription().equals("description")
                & post.getCityId() == 1);

        assertNotNull(PsqlStore.instOf().findByIdPost(id));
        when(req.getParameter("delete")).thenReturn("delete");
        psvt.doPost(req, resp);
        assertNull(PsqlStore.instOf().findByIdPost(id));
        when(req.getParameter("delete")).thenReturn(null);
    }

    @Test
    public void a1addNewPostWithAnyImgAndDeleteIt() {
        Post post;
        int id = addItem(
                post = new Post(0, "", "", new Date(), 1, 0),
                new ImgFile(1, "imagespost-noimages.png"),
                new ImgFile(1, "imagespost-0-1.png"),
                1,
                "name", "description");

        Map<Integer, String> map = PsqlStore.instOf().findAllImg(Type.POST);
        assertNotNull(PsqlStore.instOf().findByIdPost(id));
        assertEquals("anyImg", map.get(post.getPhotoId()));

        when(req.getSession().getAttribute("post")).
                thenReturn(
                        new Post(post.getId(), post.getName(), post.getDescription(),
                                post.getCreated(), post.getPhotoId(), post.getCityId())
                );
        when(req.getSession().getAttribute("oldPhoto")).
                thenReturn(new ImgFile(post.getPhotoId(), "anyImg"));
        when(req.getParameter("delete")).thenReturn("delete");
        psvt.doPost(req, resp);

        assertNull(PsqlStore.instOf().findByIdPost(post.getId()));
        assertNull(PsqlStore.instOf().findImgPost(post.getPhotoId()));

        when(req.getParameter("delete")).thenReturn(null);
    }

    @Test
    public void a2addNewPostWithAnyImgAndDeleteOnlyImage() {
        Post post;
        addItem(post = new Post(0, "", "", new Date(), 1, 0),
                new ImgFile(1, "imagespost-noimages.png"),
                new ImgFile(1, "imagespost-0-1.png"),
                1,
                "name", "description");
        int photoId = post.getPhotoId();

        addItem(post,
                new ImgFile(post.getPhotoId(), "anyImg"),
                new ImgFile(post.getPhotoId(), "imagespost-noimages.png"),
                1,
                "name", "description");

        assertEquals(1, PsqlStore.instOf().findByIdPost(post.getId()).getPhotoId());
        assertNull(PsqlStore.instOf().findAllImg(Type.POST).get(photoId));
    }

    @Test
    public void a3addNewPostWithAnyImgAndDeleteOnlyImage() {
        Post post;
        addItem(post = new Post(0, "", "", new Date(), 1, 0),
                new ImgFile(1, "imagespost-noimages.png"),
                new ImgFile(1, "imagespost-0-1.png"),
                1,
                "name", "description");
        int photoId = post.getPhotoId();

        addItem(post,
                new ImgFile(post.getPhotoId(), "anyImg"),
                new ImgFile(post.getPhotoId(), "newImage.png"),
                1,
                "name", "description");
        assertEquals(photoId,
                PsqlStore.instOf().findByIdPost(post.getId()).getPhotoId());
        assertEquals("newImage.png",
                PsqlStore.instOf().findImgPost(post.getPhotoId()).getName());
    }

    @Test
    public void a4doGetReturnAllPostsFromSQL() {
        Collection<Post> list = new ArrayList<>();
        RequestDispatcher reqDis = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(anyString())).thenReturn(reqDis);
        doAnswer(n -> {
            list.addAll(PsqlStore.instOf().findAllPosts());
            return null;
        }).when(req).setAttribute(eq("posts"), any());
        List<Post> posts = List.of(
                new Post(0, "Aname", "Adescription", new Date(100), 1, 1),
                new Post(0, "Bname", "Bdescription", new Date(100), 1, 1),
                new Post(0, "Cname", "Cdescription", new Date(100), 1, 1)
        );
        posts.forEach(n ->
                addItem(n,
                        new ImgFile(n.getPhotoId(), PsqlStore.POSTNOIMAGES),
                        new ImgFile(n.getPhotoId(), PsqlStore.POSTNOIMAGES),
                        1,
                        n.getName(), n.getDescription()));
        assertTrue(list.isEmpty());
        psvt.doGet(req, resp);
        assertTrue(list.containsAll(posts));
        assertFalse(PsqlStore.instOf().findAllImg(Type.POST).isEmpty());
    }

    @Test
    public void a5rename() throws Exception {
        File file = mock(File.class);
        whenNew(File.class).withAnyArguments().thenReturn(file);
        when(file.renameTo(any())).thenReturn(true);
        Method method = PostServlet.class.getDeclaredMethod("rename",
                String.class, int.class);
        method.setAccessible(true);
        String str = (String) method.invoke(new PostServlet(),
                "imagespost-0-picture.png", 100);
        assertEquals("imagespost-100-picture.png", str);
        str = (String) method.invoke(new PostServlet(), "picture.png", 100);
        assertEquals("picture.png-100-picture.png", str);
    }
}
