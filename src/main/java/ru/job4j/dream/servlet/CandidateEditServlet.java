package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.ImgFile;
import ru.job4j.dream.model.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

public class CandidateEditServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CandidateEditServlet.class);
    public static final String IMAGES = "images";

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        Candidate candidate;
        try {
            String id = req.getParameter("id");
            Candidate sesn = (Candidate) req.getSession().getAttribute("candidate");

            if (id == null) {
                candidate = new Candidate(0, "", "", new Date(), 1);
                setSession(req, candidate);
            } else {
                if (sesn == null || !id.equals(String.valueOf(sesn.getId()))) {
                    candidate = PsqlStore.instOf().findByIdCand(Integer.parseInt(id));
                    setSession(req, candidate);
                }
            }
            req.getRequestDispatcher("candidate/edit.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void setSession(final HttpServletRequest req, final Candidate candidate) {
        req.getSession().setAttribute("candidate", candidate);
        ImgFile imgFile = PsqlStore.instOf().findImgCand(candidate.getPhotoId());
        req.getSession().setAttribute("photo", PsqlStore.instOf().
                findImgCand(candidate.getPhotoId()));
        req.getSession().setAttribute("oldPhoto", imgFile);
    }

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        ImgFile photo = (ImgFile) req.getSession().getAttribute("photo");
        ImgFile oldPhoto = (ImgFile) req.getSession().getAttribute("oldPhoto");
        if (!photo.getName().equals(oldPhoto.getName())) {
            PsqlStore.instOf().cleanUp(Path.of(IMAGES, photo.getName()));
        }
        req.getSession().removeAttribute("candidate");
        req.getSession().removeAttribute("photo");
        req.getSession().removeAttribute("oldPhoto");
        try {
            resp.sendRedirect(req.getContextPath() + "/candidate.do");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
