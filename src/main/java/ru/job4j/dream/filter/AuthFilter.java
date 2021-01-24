package ru.job4j.dream.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    /**
     * doFilter.
     *
     * @param sreq  sreq
     * @param sresp sresp
     * @param chain chain
     * @throws IOException      IOException
     * @throws ServletException throws
     */
    @Override
    public void doFilter(final ServletRequest sreq, final ServletResponse sresp,
                         final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do") || uri.endsWith("register.do") || uri.endsWith("cities.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
        //destroy
    }
}
