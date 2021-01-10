package ru.job4j.dream.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.PsqlStore;
import ru.job4j.dream.model.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.lang.Integer.parseInt;

public class CityServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CityServlet.class);

    /**
     * doGet.
     *
     * @param req  req
     * @param resp resp
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        String city;
        String cityid = req.getParameter("cityid");
        Store sql = PsqlStore.instOf();
        if (cityid != null) {
            try {
                city = sql.findByIdCity(parseInt(cityid));
                String ref = req.getHeader("referer");
                if (ref.contains("newcandidate.do") || ref.contains("newpost.do")) {
                    city = "<option selected id = \"selected\" value ="
                            + cityid + ">"
                            + city
                            + "</option>";
                }
                out(city, resp);
            } catch (NumberFormatException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            List<String> list = sql.findAllCities();
            out(listToJson(list), resp);
        }
    }

    private void out(final String str, final HttpServletResponse resp) {
        resp.setContentType("application/json");
        //resp.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("windows-1251");
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.println(str);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String listToJson(List<String> list) {
        String json = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return json;
    }
}
