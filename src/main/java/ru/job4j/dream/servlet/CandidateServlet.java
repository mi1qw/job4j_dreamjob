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
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

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
        Candidate candidate = (Candidate) req.getSession().getAttribute("candidate");
        ImgFile oldPhoto = (ImgFile) req.getSession().getAttribute("oldPhoto");
        String oldfile = oldPhoto.getName();
        ImgFile newPhoto = (ImgFile) req.getSession().getAttribute("photo");
        String file = newPhoto.getName();
        if ("delete".equals(req.getParameter("delete"))) {
            PsqlStore.instOf().deleteByIdCand(candidate.getId());
            if (!oldfile.equals(file)) {
                PsqlStore.instOf().cleanUp(Path.of(IMAGES, file));
            }
            if (oldPhoto.getId() != 1) {
                PsqlStore.instOf().deleteImgCand(candidate.getPhotoId());
                PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
            }
        } else {
            candidate.setName(req.getParameter("name"));
            candidate.setDescription(req.getParameter("description"));
            candidate.setCityId(Integer.parseInt(req.getParameter("city")));
            if (!file.equals(oldfile)) {
                if (PsqlStore.NOIMAGES.equals(file)) {
                    int photoId = candidate.getPhotoId();
                    candidate.setPhotoId(1);
                    PsqlStore.instOf().save(candidate);
                    PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
                    PsqlStore.instOf().deleteImgCand(photoId);
                } else {
                    if (candidate.getPhotoId() == 1) {
                        if ((candidate.getId() == 0)) {
                            PsqlStore.instOf().save(candidate);
                            file = rename(file, candidate.getId());
                        }
                        int photoId = PsqlStore.instOf().saveImgCand(file, candidate);
                        candidate.setPhotoId(photoId);
                    } else {
                        PsqlStore.instOf().saveImgCand(file, candidate);
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
            ArrayList<Candidate> list = (ArrayList<Candidate>) PsqlStore.instOf().
                    findAllCandidates();
            list.sort(Comparator.comparing(Candidate::getCreated).reversed());
            req.setAttribute("candidates", list);
            req.setAttribute("candidatesPhoto", PsqlStore.instOf().findAllImg(Type.CANDIDATE));
            req.getRequestDispatcher("candidate/candidates.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String rename(final String img, final int id) {
        String folder = getFolder(img);
        String name = folder + "-" + id + "-" + getName(img);
        File file = new File(folder + File.separator + name);
        File old = new File(folder + File.separator + img);
        if (!old.renameTo(file)) {
            LOGGER.error("Failed to rename");
        }
        return name;
    }

    private String getName(final String name) {
        String[] m = name.split("-", 3);
        return m[m.length - 1];
    }

    private String getFolder(final String name) {
        int n = name.indexOf("-");
        if (n != -1) {
            return name.substring(0, n);
        }
        return name;
    }
}
