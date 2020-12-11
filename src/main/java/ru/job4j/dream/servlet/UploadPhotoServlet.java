package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.ImgFile;
import ru.job4j.dream.model.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UploadPhotoServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(UploadPhotoServlet.class);
    public static final String IMAGES = "images";
    private final SimpleDateFormat time = new SimpleDateFormat("yyyy_M_dd_HH_mm_ss_SSS");

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
        //String id = req.getParameter("id");
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
        //System.out.println(req.getParameter("photo"));
        //req.setAttribute("photo", req.getParameter("photo"));
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
        ImgFile newPhoto = (ImgFile) req.getSession().getAttribute("photo");
        if ("delete".equals(req.getParameter("delete"))) {
            //newPhoto.setName(PsqlStore.instOf().findImgCand(1).getName());
            newPhoto.setName(PsqlStore.getNoimage());
        } else {
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
            try {
                assert items != null;
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        if (!validate(item.getName())) {
                            throw new IllegalArgumentException("Wrong file name !");
                        }
                        File file = new File(folder + File.separator + rename(item.getName()));
                        newPhoto.setName(file.getName());
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            out.write(item.getInputStream().readAllBytes());
                        } catch (IOException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        Candidate sesn = (Candidate) req.getSession().getAttribute("candidate");
        try {
            resp.sendRedirect(req.getContextPath() + "/newcandidate.do" + "?id=" + sesn.getId());
        } catch (IOException e) {
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

    private synchronized String rename(final String img) {
        String date = time.format(new Date(System.currentTimeMillis()));
        return date.concat("-").concat(img);
    }
}
