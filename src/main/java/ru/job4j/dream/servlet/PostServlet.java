package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostServlet.class);

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
            Store.instOf().save(
                    new Post(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name"),
                            req.getParameter("description"),
                            ""
                    ));
            resp.sendRedirect(req.getContextPath() + "/post/posts.jsp");
        } catch (IOException | NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
