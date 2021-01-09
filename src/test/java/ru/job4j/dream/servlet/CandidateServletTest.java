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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.*;

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
@PrepareForTest({BasicDataSource.class, PsqlStore.class, CandidateServlet.class})
public class CandidateServletTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(CandidateServletTest.class);
    private static Connection conn;

    static {
        try {
            conn = ConnectionRollback.create(ConnectionRollback.init());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static HttpServletRequest req;
    private static HttpServletResponse resp;
    private static HttpSession ssn;
    private static CandidateServlet csvt;
    private static PsqlStore psql;

    @Before
    public void before() throws Exception {
        BasicDataSource bds = mock(BasicDataSource.class);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        ssn = mock(HttpSession.class);

        when(bds.getConnection()).thenReturn(conn);
        whenNew(BasicDataSource.class).withNoArguments().thenReturn(bds);
        when(req.getSession()).thenReturn(ssn);

        spy(PsqlStore.class);
        psql = spy(new PsqlStore());
        when(PsqlStore.instOf()).thenReturn(psql);
        doNothing().when(psql).cleanUp(any());

        spy(CandidateServlet.class);
        csvt = spy(new CandidateServlet());
        doReturn("anyImg").when(csvt, "rename", anyString(), anyInt());
    }

    @AfterClass
    public static void end() throws SQLException {
        conn.close();
    }

    private int addItem(final Candidate cand, final ImgFile oldPhoto, final ImgFile newPhoto,
                        final int cityId, final String... param) {

        //todo может не нужен post в методах
        when(req.getSession().getAttribute("candidate")).thenReturn(cand);
        when(req.getSession().getAttribute("oldPhoto")).thenReturn(oldPhoto);
        when(req.getSession().getAttribute("photo")).thenReturn(newPhoto);

        when(req.getParameter("name")).thenReturn(param[0]);
        when(req.getParameter("description")).thenReturn(param[1]);
        when(req.getParameter("city")).thenReturn(String.valueOf(cityId));

        csvt.doPost(req, resp);
        return cand.getId();
    }

    @Test
    public void a0addNewCandidateWithoutImgAndDelete() {       // добавить/удалить cand без фото
        Candidate cand = new Candidate(0, "", "", new Date(), 1, 0);
        ImgFile oldPhoto = new ImgFile(1, "images-noimages.png");
        ImgFile newPhoto = new ImgFile(1, "images-noimages.png");

        int id = addItem(cand, oldPhoto, newPhoto, 1, "name", "description");
        assertTrue(cand.getName().equals("name") & cand.getDescription().equals("description")
                & cand.getCityId() == 1);

        assertNotNull(PsqlStore.instOf().findByIdCand(id));
        when(req.getParameter("delete")).thenReturn("delete");
        csvt.doPost(req, resp);
        assertNull(PsqlStore.instOf().findByIdCand(id));
        when(req.getParameter("delete")).thenReturn(null);
    }

    @Test
    public void a1addNewCandidateWithAnyImgAndDeleteIt() {
        Candidate cand;
        int id = addItem(
                cand = new Candidate(0, "", "", new Date(), 1, 0),
                new ImgFile(1, "images-noimages.png"),
                new ImgFile(1, "images-0-1.png"),
                1,
                "name", "description");

        Map<Integer, String> map = PsqlStore.instOf().findAllImg(Type.CANDIDATE);
        assertNotNull(PsqlStore.instOf().findByIdCand(id));
        assertEquals("anyImg", map.get(cand.getPhotoId()));

        when(req.getSession().getAttribute("candidate")).
                thenReturn(
                        new Candidate(cand.getId(), cand.getName(), cand.getDescription(),
                                cand.getCreated(), cand.getPhotoId(), cand.getCityId())
                );
        when(req.getSession().getAttribute("oldPhoto")).
                thenReturn(new ImgFile(cand.getPhotoId(), "anyImg"));
        when(req.getParameter("delete")).thenReturn("delete");
        csvt.doPost(req, resp);

        assertNull(PsqlStore.instOf().findByIdCand(cand.getId()));
        assertNull(PsqlStore.instOf().findImgCand(cand.getPhotoId()));

        when(req.getParameter("delete")).thenReturn(null);
    }

    @Test
    public void a2addNewCandidateWithAnyImgAndDeleteOnlyImage() {
        Candidate candidate;
        addItem(candidate = new Candidate(0, "", "", new Date(), 1, 0),
                new ImgFile(1, "images-noimages.png"),
                new ImgFile(1, "images-0-1.png"),
                1,
                "name", "description");
        int photoId = candidate.getPhotoId();

        addItem(candidate,
                new ImgFile(candidate.getPhotoId(), "anyImg"),
                new ImgFile(candidate.getPhotoId(), "images-noimages.png"),
                1,
                "name", "description");

        assertEquals(1, PsqlStore.instOf().findByIdCand(candidate.getId()).getPhotoId());
        assertNull(PsqlStore.instOf().findAllImg(Type.CANDIDATE).get(photoId));
    }

    @Test
    public void a3addNewCandidateWithAnyImgAndDeleteOnlyImage() {
        Candidate candidate;
        addItem(candidate = new Candidate(0, "", "", new Date(), 1, 0),
                new ImgFile(1, "images-noimages.png"),
                new ImgFile(1, "images-0-1.png"),
                1,
                "name", "description");
        int photoId = candidate.getPhotoId();

        addItem(candidate,
                new ImgFile(candidate.getPhotoId(), "anyImg"),
                new ImgFile(candidate.getPhotoId(), "newImage.png"),
                1,
                "name", "description");
        assertEquals(photoId,
                PsqlStore.instOf().findByIdCand(candidate.getId()).getPhotoId());
        assertEquals("newImage.png",
                PsqlStore.instOf().findImgCand(candidate.getPhotoId()).getName());
    }

    @Test
    public void a4doGetReturnAllCandidatesFromSQL() {
        Collection<Candidate> list = new ArrayList<>();
        RequestDispatcher reqDis = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(anyString())).thenReturn(reqDis);
        doAnswer(n -> {
            list.addAll(PsqlStore.instOf().findAllCandidates());
            return null;
        }).when(req).setAttribute(eq("candidates"), any());
        List<Candidate> candidates = List.of(
                new Candidate(0, "Aname", "Adescription", new Date(100), 1, 1),
                new Candidate(0, "Bname", "Bdescription", new Date(100), 1, 1),
                new Candidate(0, "Cname", "Cdescription", new Date(100), 1, 1)
        );
        candidates.forEach(n ->
                addItem(n,
                        new ImgFile(n.getPhotoId(), PsqlStore.NOIMAGES),
                        new ImgFile(n.getPhotoId(), PsqlStore.NOIMAGES),
                        1,
                        n.getName(), n.getDescription()));
        assertTrue(list.isEmpty());
        csvt.doGet(req, resp);
        assertTrue(list.containsAll(candidates));
        assertFalse(PsqlStore.instOf().findAllImg(Type.CANDIDATE).isEmpty());
    }

    @Test
    public void a5rename() throws Exception {
        File file = mock(File.class);
        whenNew(File.class).withAnyArguments().thenReturn(file);
        when(file.renameTo(any())).thenReturn(true);
        Method method = CandidateServlet.class.getDeclaredMethod("rename",
                String.class, int.class);
        method.setAccessible(true);
        String str = (String) method.invoke(new CandidateServlet(),
                "images-0-picture.png", 100);
        assertEquals("images-100-picture.png", str);
        str = (String) method.invoke(new CandidateServlet(), "picture.png", 100);
        assertEquals("picture.png-100-picture.png", str);
    }
}
