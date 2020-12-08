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
import java.util.List;

public class UploadPhotoServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(UploadPhotoServlet.class);
    public static final String IMAGES = "images";

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {

        //System.out.println(req.getSession().getAttribute("candidateSS") + "   candidateSS");
        //Candidate candidate;
        String id = req.getParameter("id");
        //candidate = req.getParameter("candidate");

        //if (!id.equals("0")) {
        //    candidate = PsqlStore.instOf().findByIdCand(Integer.parseInt(id));
        //} else {
        //    candidate = new Candidate(0, "", "", new Date(), 1);
        //}
        //req.setAttribute("candidate", PsqlStore.instOf().findByIdCand(Integer.parseInt(id)));
        //req.setAttribute("photo", PsqlStore.instOf().findImgCand(Integer.parseInt(id)));
        //req.setAttribute("candidate", candidate);
        //req.setAttribute("photo", PsqlStore.instOf().findImgCand(candidate.getPhotoId()));
        System.out.println(req.getParameter("photo"));
        req.setAttribute("photo", req.getParameter("photo"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/photos/upload.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (IOException | ServletException e) {
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
        String id = req.getParameter("id");
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
        assert items != null;
        for (FileItem item : items) {
            if (!item.isFormField()) {
                String name = item.getName();
                if (!validate(name)) {
                    LOGGER.error("Wrong file name");
                    doGet(req, resp);
                }
                File file = new File(folder + File.separator + item.getName());
                System.out.println(file.getName());
                try (FileOutputStream out = new FileOutputStream(file)) {
                    out.write(item.getInputStream().readAllBytes());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        //doGet(req, resp);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/newcandidate.do");
        try {
            dispatcher.forward(req, resp);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Validate image name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean validate(final String name) {
        return name.matches("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)");
    }
}
