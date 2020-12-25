package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.ImgFile;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Post post;
        try {
            String id = req.getParameter("id");
            Post sesn = (Post) req.getSession().getAttribute("post");

            if (id == null) {
                post = new Post(0, "", "", new Date(), 1);
                setSession(req, post);
            } else {
                if (sesn == null || !id.equals(String.valueOf(sesn.getId()))) {
                    post = PsqlStore.instOf().findByIdPost(Integer.parseInt(id));
                    setSession(req, post);
                }
            }
            req.getRequestDispatcher("post/edit.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void setSession(final HttpServletRequest req, final Post post) {
        req.getSession().setAttribute("post", post);
        ImgFile imgFile = PsqlStore.instOf().findImgPost(post.getPhotoId());
        req.getSession().setAttribute("photo", PsqlStore.instOf().
                findImgPost(post.getPhotoId()));
        req.getSession().setAttribute("oldPhoto", imgFile);
        req.getSession().setAttribute("newPhoto", new ImgFile(0, null));
        //todo нужен ли newPhoto ?
    }

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        ImgFile photo = (ImgFile) req.getSession().getAttribute("photo");
        ImgFile oldPhoto = (ImgFile) req.getSession().getAttribute("oldPhoto");
        if (!photo.getName().equals(oldPhoto.getName())) {
            PsqlStore.instOf().cleanUp(Path.of(IMAGES, photo.getName()));
        }
        req.getSession().removeAttribute("post");
        req.getSession().removeAttribute("photo");
        req.getSession().removeAttribute("oldPhoto");
        req.getSession().removeAttribute("newPhoto");
        try {
            resp.sendRedirect(req.getContextPath() + "/post.do");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
