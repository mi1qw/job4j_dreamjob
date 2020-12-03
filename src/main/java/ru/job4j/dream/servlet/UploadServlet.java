package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(UploadServlet.class);
    public static final String IMAGES = "images";

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        List<String> images = new ArrayList<>();

        File folder = new File(IMAGES);
        if (!folder.exists()) {
            folder.mkdir();
        }
        System.out.println("qqqqqqqqqqqqqqqqqqq");
        File[] list = new File(IMAGES).listFiles();
        System.out.println("qqqqqqqqqqqqqqqqqqq");
        System.out.println(req.getContextPath());
        System.out.println(req.getRequestURI());
        System.out.println(req.getRequestURL());
        if (list != null) {
            for (File name : list) {
                images.add(name.getName());
            }
            req.setAttribute(IMAGES, images);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/upload.jsp");
            try {
                dispatcher.forward(req, resp);
            } catch (IOException | ServletException e) {
                LOGGER.error(e.getMessage(), e);
            }
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
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            LOGGER.error(e.getMessage(), e);
        }
        File folder = new File(IMAGES);
        if (!folder.exists()) {
            folder.mkdir();
        }
        for (FileItem item : items) {
            if (!item.isFormField()) {
                File file = new File(folder + File.separator + item.getName());
                try (FileOutputStream out = new FileOutputStream(file)) {
                    out.write(item.getInputStream().readAllBytes());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        doGet(req, resp);
    }
}
