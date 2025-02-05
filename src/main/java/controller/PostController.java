package controller;

import com.google.gson.Gson;
import model.Post;
import service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
        public static final String APPLICATION_JSON = "application/json";
        private final PostService service;

        public PostController(PostService service) {
            this.service = service;
        }

        public void all(HttpServletResponse response) throws IOException {
            service.sendJsonResponse(response,service.all());
        }

        public void getById(long id, HttpServletResponse response) throws IOException {
            service.sendJsonResponse(response,service.getById(id));
        }

        public void save(Reader body, HttpServletResponse response) throws IOException {
            final var post = new Gson().fromJson(body, Post.class);
            service.sendJsonResponse(response,service.save(post));
        }

        public void removeById(long id, HttpServletResponse response) throws IOException {
            response.setContentType(APPLICATION_JSON);
            service.removeById(id);
            response.getWriter().print("Удалено!");
        }
    }

