package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.ImgFile;
import ru.job4j.dream.model.PsqlStore;
import ru.job4j.dream.model.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

public class CandidateEditServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CandidateEditServlet.class);
    public static final String IMAGES = PsqlStore.IMAGES;
    private static final String CANDIDATE = "candidate";
    private static final String PHOTO = "photo";
    private static final String OLD_PHOTO = "oldPhoto";

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession ss = req.getSession();
        Candidate candidate;
        try {
            String id = req.getParameter("id");
            Candidate sesn = (Candidate) ss.getAttribute(CANDIDATE);
            ImgFile photo = (ImgFile) ss.getAttribute(PHOTO);

            if (id == null) {
                candidate = new Candidate(0, "", "", new Date(), 1, 0);
                setSession(ss, candidate);
            } else {
                if (sesn == null || !id.equals(String.valueOf(sesn.getId()))) {
                    candidate = PsqlStore.instOf().findByIdCand(Integer.parseInt(id));
                    setSession(ss, candidate);
                } else if (sesn.getPhotoId() != photo.getId()) {
                    setSession(ss, sesn);
                }
            }
            req.getRequestDispatcher("candidate/edit.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void setSession(final HttpSession ss, final Candidate candidate) {
        ss.setAttribute(CANDIDATE, candidate);
        ImgFile imgFile = PsqlStore.instOf().findImgCand(candidate.getPhotoId());
        ss.setAttribute(PHOTO, PsqlStore.instOf().
                findImgCand(candidate.getPhotoId()));
        ss.setAttribute(OLD_PHOTO, imgFile);
    }

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession ss = req.getSession();
        ImgFile photo = (ImgFile) ss.getAttribute(PHOTO);
        ImgFile oldPhoto = (ImgFile) ss.getAttribute(OLD_PHOTO);
        PsqlStore.instOf().clearListImg(ss, photo, Type.CANDIDATE);
        if (!photo.getName().equals(oldPhoto.getName())) {
            PsqlStore.instOf().cleanUp(Path.of(IMAGES, photo.getName()));
        }
        ss.removeAttribute(CANDIDATE);
        ss.removeAttribute(PHOTO);
        ss.removeAttribute(OLD_PHOTO);
        try {
            resp.sendRedirect(req.getContextPath() + "/candidate.do");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
