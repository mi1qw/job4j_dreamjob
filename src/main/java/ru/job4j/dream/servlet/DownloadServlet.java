package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(DownloadServlet.class);

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        String name = req.getParameter("name");
        String folder = req.getParameter("folder");
        resp.setContentType("name=" + name);
        resp.setContentType("image/png");
        File file = new File(getFolder(name) + File.separator + name);
        //File file = new File(getFolder(name) + File.separator + name);
        //File file = new File("images" + File.separator + name);
`        try (FileInputStream in = new FileInputStream(file)) {
            resp.setHeader("Content-Disposition", "attachment; filename=\""
                    + getName(name)
                    + "\"");
            resp.getOutputStream().write(in.readAllBytes());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String getName(final String name) {
        String[] m = name.split("-", 3);
        //int n = 0;
        //if (n != -1) {
        //    return name.substring(n + 1);
        //}
        //return name;
        return m[m.length - 1];
    }
    //private String getName(final String name) {
    //    int n = name.indexOf("-");
    //    if (n != -1) {
    //        return name.substring(n + 1);
    //    }
    //    return name;
    //}

    private String getFolder(final String name) {
        int n = name.indexOf("-");
        if (n != -1) {
            return name.substring(0, n);
        }
        return name;
    }
}
