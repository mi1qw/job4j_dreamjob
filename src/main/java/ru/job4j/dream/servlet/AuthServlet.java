package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthServlet.class);

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            if ("root@local".equals(email) && "root".equals(password)) {
                HttpSession sc = req.getSession();
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail(email);
                sc.setAttribute("user", admin);
                resp.sendRedirect(req.getContextPath() + "/posts.do");
            } else {
                req.setAttribute("error", "Не верный email или пароль");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (IOException | ServletException e) {
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
        //req.getSession().removeAttribute("user");
        doPost(req, resp);
    }
}
