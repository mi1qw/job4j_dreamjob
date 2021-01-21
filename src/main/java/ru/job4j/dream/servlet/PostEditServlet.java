package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.ImgFile;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.PsqlStore;
import ru.job4j.dream.model.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

public class PostEditServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostEditServlet.class);
    public static final String IMAGES = PsqlStore.IMAGESPOST;

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession ss = req.getSession();
        Post post = null;
        try {
            String id = req.getParameter("id");
            Post sesn = (Post) ss.getAttribute("post");
            ImgFile photo = (ImgFile) ss.getAttribute("photo");

            if (id == null) {
                post = new Post(0, "", "", new Date(), 1, 0);
                setSession(ss, post);
            } else {
                if (sesn == null || !id.equals(String.valueOf(sesn.getId()))) {
                    post = PsqlStore.instOf().findByIdPost(Integer.parseInt(id));
                    setSession(ss, post);
                } else if (sesn.getPhotoId() != photo.getId()) {
                    setSession(ss, sesn);
                }
            }
            req.getRequestDispatcher("post/edit.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void setSession(final HttpSession ss, final Post post) {
        ss.setAttribute("post", post);
        ImgFile imgFile = PsqlStore.instOf().findImgPost(post.getPhotoId());
        ss.setAttribute("photo", PsqlStore.instOf().
                findImgPost(post.getPhotoId()));
        ss.setAttribute("oldPhoto", imgFile);
    }

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        HttpSession ss = req.getSession();
        ImgFile photo = (ImgFile) ss.getAttribute("photo");
        ImgFile oldPhoto = (ImgFile) ss.getAttribute("oldPhoto");
        PsqlStore.instOf().clearListImg(ss, photo, Type.POST);
        if (!photo.getName().equals(oldPhoto.getName())) {
            PsqlStore.instOf().cleanUp(Path.of(IMAGES, photo.getName()));
        }
        ss.removeAttribute("post");
        ss.removeAttribute("photo");
        ss.removeAttribute("oldPhoto");
        try {
            resp.sendRedirect(req.getContextPath() + "/post.do");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
