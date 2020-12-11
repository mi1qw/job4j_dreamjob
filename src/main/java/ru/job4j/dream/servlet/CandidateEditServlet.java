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
import java.util.Date;

public class CandidateEditServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CandidateEditServlet.class);

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        Candidate candidate;
        System.out.println();

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

            //(sesn != null && id.equals(String.valueOf(sesn.getId())))){
            //    //if (req.getSession().getAttribute("candidate") == null) {
            //
            //    if (id != null) {
            //
            //    } else {        // it`s a new candidate with id=0 and photoID=1
            //
            //    }
            //    //req.getSession().setAttribute("candidate", candidate);
            //    //ImgFile imgFile = PsqlStore.instOf().findImgCand(candidate.getPhotoId());
            //    //req.getSession().setAttribute("photo", PsqlStore.instOf().
            //    //        findImgCand(candidate.getPhotoId()));
            //    //req.getSession().setAttribute("oldPhoto", imgFile);
            //    //req.getSession().setAttribute("newPhoto", new ImgFile(0, null));
            //    //}
            //    //}
            //}

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
        req.getSession().setAttribute("newPhoto", new ImgFile(0, null));
    }

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        req.getSession().removeAttribute("candidate");
        req.getSession().removeAttribute("photo");
        req.getSession().removeAttribute("oldPhoto");
        req.getSession().removeAttribute("newPhoto");

        //ImgFile newPhoto = (ImgFile) req.getSession().getAttribute("photo");
        //String file = newPhoto.getName();
        //if (file.equals("Delete")) {
        //    System.out.println("equals(\"Delete\")");
        //} else {
        //    Candidate candidate = (Candidate) req.getSession().getAttribute("candidate");
        //    int photoId = PsqlStore.instOf().saveImgCand(file, candidate);
        //    if (candidate.getPhotoId() != 1) {
        //        ImgFile img = (ImgFile) req.getSession().getAttribute("photo");
        //        PsqlStore.instOf().cleanUp(Path.of("images", img.getName()));
        //        //File img = new File(req.getContextPath() + File.separator + imgName.getName());
        //        //img.delete();
        //    } else {
        //        candidate.setPhotoId(photoId);
        //        PsqlStore.instOf().save(candidate);
        //    }
        //}

        try {
            resp.sendRedirect(req.getContextPath() + "/candidate.do");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        //this.doGet(req, resp);

        //try {
        //    req.getRequestDispatcher("candidate.do").forward(req, resp);
        //} catch (IOException | ServletException e) {
        //    LOGGER.error(e.getMessage(), e);
        //}

    }
}
