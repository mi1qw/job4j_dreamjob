package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.ImgFile;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UploadPhotoServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(UploadPhotoServlet.class);
    private final SimpleDateFormat time = new SimpleDateFormat("yyyy_M_dd_HH_mm_ss_SSS");

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
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
        try {
            int id;
            String images;
            String noimage;
            HttpSession ss = req.getSession();
            ImgFile newPhoto = (ImgFile) ss.getAttribute("photo");
            Candidate candidate = (Candidate) ss.getAttribute("candidate");
            if (candidate == null) {
                images = PsqlStore.IMAGESPOST;
                id = ((Post) ss.getAttribute("post")).getId();
                noimage = PsqlStore.POSTNOIMAGES;
            } else {
                images = PsqlStore.IMAGES;
                id = candidate.getId();
                noimage = PsqlStore.NOIMAGES;
            }
            if ("delete".equals(req.getParameter("delete"))) {
                newPhoto.setName(noimage);
            } else {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletContext servletContext = this.getServletConfig().getServletContext();
                File repository = (File) servletContext.
                        getAttribute("javax.servlet.context.tempdir");
                factory.setRepository(repository);
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = null;
                try {
                    items = upload.parseRequest(req);
                } catch (FileUploadException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                File folder = new File(images);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        if (!validate(item.getName())) {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                            throw new IllegalArgumentException("Wrong file name !");
                        }
                        File file = new File(folder + File.separator
                                //+ rename(item.getName(), id));
                                + rename(item.getName(), id, images));
                        newPhoto.setName(file.getName());
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            out.write(item.getInputStream().readAllBytes());
                        } catch (IOException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                }
            }
            if (candidate == null) {
                resp.sendRedirect(req.getContextPath() + "/newpost.do" + "?id=" + id);
            } else {
                resp.sendRedirect(req.getContextPath() + "/newcandidate.do" + "?id=" + id);
            }
        } catch (IOException | IllegalArgumentException e) {
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

    private String rename(final String img, final int id) {
        return String.valueOf(id).concat("-").concat(img);
    }

    private String rename(final String img, final int id, final String folder) {
        return folder.concat("-").concat(String.valueOf(id)).concat("-").concat(img);
    }
}
