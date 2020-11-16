package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            Store.instOf().saveCandidate(
                    new Candidate(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name"),
                            req.getParameter("description"),
                            ""
                    ));
            resp.sendRedirect(req.getContextPath() + "/candidate/candidates.jsp");
        } catch (IOException | NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
