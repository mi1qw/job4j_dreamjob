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
        HttpSession sc = req.getSession();
        try {
            if (sc.getAttribute("user") != null) {
                resp.sendRedirect(req.getContextPath() + "/candidate.do");
            } else {
                Store sql = PsqlStore.instOf();
                User user = sql.findByEmail(email);
                if (user != null && user.getPassword().equals(password)) {
                    sc.setAttribute("user", user);
                    resp.sendRedirect(req.getContextPath() + "/candidate.do");
                } else {
                    req.setAttribute("error", "Неверный e-mail или пароль");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
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
        try {
            req.getSession().removeAttribute("user");
            resp.sendRedirect("login.jsp");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
