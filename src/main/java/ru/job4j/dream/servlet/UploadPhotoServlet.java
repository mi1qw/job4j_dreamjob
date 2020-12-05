package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class UploadPhotoServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(UploadPhotoServlet.class);

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        Candidate candidate;
        String id = req.getParameter("id");
        //candidate = req.getParameter("candidate");

        if (!id.equals("0")) {
            candidate = PsqlStore.instOf().findByIdCand(Integer.parseInt(id));
        } else {
            candidate = new Candidate(0, "", "", new Date(), 1);
        }
        //req.setAttribute("candidate", PsqlStore.instOf().findByIdCand(Integer.parseInt(id)));
        //req.setAttribute("photo", PsqlStore.instOf().findImgCand(Integer.parseInt(id)));
        req.setAttribute("candidate", candidate);
        req.setAttribute("photo", PsqlStore.instOf().findImgCand(candidate.getPhotoId()));
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
        //DiskFileItemFactory factory = new DiskFileItemFactory();
        //ServletContext servletContext = this.getServletConfig().getServletContext();
        //File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        //factory.setRepository(repository);
        //ServletFileUpload upload = new ServletFileUpload(factory);
        //List<FileItem> items = null;
        //try {
        //    items = upload.parseRequest(req);
        //} catch (FileUploadException e) {
        //    LOGGER.error(e.getMessage(), e);
        //}
        //File folder = new File(IMAGES);
        //if (!folder.exists()) {
        //    folder.mkdir();
        //}
        //for (FileItem item : items) {
        //    if (!item.isFormField()) {
        //        File file = new File(folder + File.separator + item.getName());
        //        try (FileOutputStream out = new FileOutputStream(file)) {
        //            out.write(item.getInputStream().readAllBytes());
        //        } catch (IOException e) {
        //            LOGGER.error(e.getMessage(), e);
        //        }
        //    }
        //}
        doGet(req, resp);
    }
}
