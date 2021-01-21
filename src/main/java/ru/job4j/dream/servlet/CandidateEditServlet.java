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

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession ss = req.getSession();
        Candidate candidate = null;
        try {
            String id = req.getParameter("id");
            Candidate sesn = (Candidate) ss.getAttribute("candidate");
            ImgFile photo = (ImgFile) ss.getAttribute("photo");

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
        ss.setAttribute("candidate", candidate);
        ImgFile imgFile = PsqlStore.instOf().findImgCand(candidate.getPhotoId());
        ss.setAttribute("photo", PsqlStore.instOf().
                findImgCand(candidate.getPhotoId()));
        ss.setAttribute("oldPhoto", imgFile);
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
        ImgFile photo = (ImgFile) ss.getAttribute("photo");
        ImgFile oldPhoto = (ImgFile) ss.getAttribute("oldPhoto");
        PsqlStore.instOf().clearListImg(ss, photo, Type.CANDIDATE);
        if (!photo.getName().equals(oldPhoto.getName())) {
            PsqlStore.instOf().cleanUp(Path.of(IMAGES, photo.getName()));
        }
        ss.removeAttribute("candidate");
        ss.removeAttribute("photo");
        ss.removeAttribute("oldPhoto");
        try {
            resp.sendRedirect(req.getContextPath() + "/candidate.do");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
