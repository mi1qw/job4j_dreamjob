package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
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
        String photo = req.getParameter("file");
        System.out.println(photo);
        try {
            if (photo != null) {
                req.setAttribute("photo", photo);
            } else {
                String id = req.getParameter("id");
                if (id != null) {
                    candidate = PsqlStore.instOf().findByIdCand(Integer.parseInt(id));
                } else {
                    candidate = new Candidate(0, "", "", new Date(), 1);
                }
                req.getSession().setAttribute("candidate", candidate);

                //req.setAttribute("candidate", candidate);
                req.setAttribute("photo", PsqlStore.instOf().findImgCand(candidate.getPhotoId()));
            }

            req.getRequestDispatcher("candidate/edit.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        this.doGet(req, resp);
    }
}
