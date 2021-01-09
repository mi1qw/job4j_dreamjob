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
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

public class PostServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostServlet.class);
    public static final String IMAGES = PsqlStore.IMAGESPOST;

    /**
     * doPost.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        Post post = (Post) req.getSession().getAttribute("post");
        ImgFile oldPhoto = (ImgFile) req.getSession().getAttribute("oldPhoto");
        String oldfile = oldPhoto.getName();
        ImgFile newPhoto = (ImgFile) req.getSession().getAttribute("photo");
        String file = newPhoto.getName();
        if ("delete".equals(req.getParameter("delete"))) {
            PsqlStore.instOf().deleteByIdPost(post.getId());
            if (!oldfile.equals(file)) {
                PsqlStore.instOf().cleanUp(Path.of(IMAGES, file));
            }
            if (oldPhoto.getId() != 1) {
                PsqlStore.instOf().deleteImgPost(post.getPhotoId());
                PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
            }
        } else {
            post.setName(req.getParameter("name"));
            post.setDescription(req.getParameter("description"));
            post.setCityId(Integer.parseInt(req.getParameter("city")));
            if (!file.equals(oldfile)) {
                if (PsqlStore.POSTNOIMAGES.equals(file)) {
                    int photoId = post.getPhotoId();
                    post.setPhotoId(1);
                    PsqlStore.instOf().save(post);
                    PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
                    PsqlStore.instOf().deleteImgPost(photoId);
                } else {
                    if (post.getPhotoId() == 1) {
                        if ((post.getId() == 0)) {
                            PsqlStore.instOf().save(post);
                            file = rename(file, post.getId());
                        }
                        int photoId = PsqlStore.instOf().saveImgPost(file, post);
                        post.setPhotoId(photoId);
                    } else {
                        PsqlStore.instOf().saveImgPost(file, post);
                        PsqlStore.instOf().cleanUp(Path.of(IMAGES, oldfile));
                    }
                    PsqlStore.instOf().save(post);
                }
            } else {
                PsqlStore.instOf().save(post);
            }
        }
        req.getSession().removeAttribute("post");
        req.getSession().removeAttribute("photo");
        req.getSession().removeAttribute("oldPhoto");
        try {
            req.setCharacterEncoding("UTF-8");
            resp.sendRedirect(req.getContextPath() + "/post.do");
        } catch (IOException | NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            ArrayList<Post> list = (ArrayList<Post>) PsqlStore.instOf().findAllPosts();
            list.sort(Comparator.comparing(Post::getCreated).reversed());
            req.setAttribute("posts", list);
            req.setAttribute("postsPhoto", PsqlStore.instOf().findAllImg(Type.POST));
            req.getRequestDispatcher("post/posts.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String rename(final String img, final int id) {
        String folder = getFolder(img);
        String name = folder + "-" + id + "-" + getName(img);
        File file = new File(folder + File.separator + name);
        File old = new File(folder + File.separator + img);
        if (!old.renameTo(file)) {
            LOGGER.error("Failed to rename");
        }
        return name;
    }

    private String getName(final String name) {
        String[] m = name.split("-", 3);
        return m[m.length - 1];
    }

    private String getFolder(final String name) {
        int n = name.indexOf("-");
        if (n != -1) {
            return name.substring(0, n);
        }
        return name;
    }
}
