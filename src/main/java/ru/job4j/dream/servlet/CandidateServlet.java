package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.PsqlStore;
import ru.job4j.dream.model.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CandidateServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CandidateServlet.class);

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            PsqlStore.instOf().save(
                    new Candidate(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name"),
                            req.getParameter("description"),
                            new Date(),
                            Integer.parseInt(req.getParameter("photo"))
                            //TODO: photo
                    ));
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
