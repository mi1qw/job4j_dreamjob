package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ImageServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(ImageServlet.class);

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        resp.setContentType("image/jpg");                                   //"DyT.jpg"
        try (ServletOutputStream out = resp.getOutputStream();
            InputStream in = getServletContext().getResourceAsStream("images/Steve-Carell.jpg")) {
            out.write(in.readAllBytes());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
