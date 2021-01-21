package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.PsqlStore;
import ru.job4j.dream.model.Store;
import ru.job4j.dream.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewuserServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(NewuserServlet.class);

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("newuser.jsp").forward(req, resp);
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
        try {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Store sql = PsqlStore.instOf();
            User user = sql.findByEmail(email);
            if (user != null && !password.equals(user.getPassword())) {
                req.setAttribute("error", "Этот e-mail уже занят");
                req.getRequestDispatcher("newuser.jsp").forward(req, resp);
            } else {
                if (user == null) {
                    user = new User(name, email, password);
                    sql.saveUser(user);
                }
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("auth.do").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
