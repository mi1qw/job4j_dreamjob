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
import java.io.IOException;
import java.nio.file.Path;

public class CandidateServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CandidateServlet.class);
    public static final String IMAGES = "images";

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {

        System.out.println(req.getSession().getId() + "    .getSession().getId()");

        Candidate candidate = (Candidate) req.getSession().getAttribute("candidate");
        ImgFile oldPhoto = (ImgFile) req.getSession().getAttribute("oldPhoto");
        String oldfile = oldPhoto.getName();
        System.out.println(candidate);
        if ("delete".equals(req.getParameter("delete"))) {
            PsqlStore.instOf().deleteByIdCand(candidate.getId());
            if (oldPhoto.getId() != 1) {
                PsqlStore.instOf().deleteImgCand(candidate.getPhotoId());
                PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
            }
        } else {
            candidate.setName(req.getParameter("name"));
            candidate.setDescription(req.getParameter("description"));
            System.out.println(candidate);

            ImgFile newPhoto = (ImgFile) req.getSession().getAttribute("photo");
            String file = newPhoto.getName();
            System.out.println(newPhoto.getId());

            System.out.println(oldPhoto.getId());
            if (!file.equals(oldfile)) {
                if (PsqlStore.getNoimage().equals(file)) {
                    int photoIdid = candidate.getPhotoId();
                    candidate.setPhotoId(1);
                    PsqlStore.instOf().save(candidate);
                    PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
                    PsqlStore.instOf().deleteImgCand(photoIdid);
                } else {
                    int photoId = PsqlStore.instOf().saveImgCand(file, candidate);
                    if (candidate.getPhotoId() == 1) {
                        candidate.setPhotoId(photoId);
                    } else {
                        PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
                    }
                    PsqlStore.instOf().save(candidate);
                }
            } else {
                PsqlStore.instOf().save(candidate);
            }
        }
        req.getSession().removeAttribute("candidate");
        req.getSession().removeAttribute("photo");
        req.getSession().removeAttribute("oldPhoto");
        req.getSession().removeAttribute("newPhoto");
        try {
            req.setCharacterEncoding("UTF-8");
            resp.sendRedirect(req.getContextPath() + "/candidate.do");
        } catch (IOException | NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
            req.setAttribute("candidatesPhoto", PsqlStore.instOf().findAllImg(Type.CANDIDATE));
            req.getRequestDispatcher("candidate/candidates.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
