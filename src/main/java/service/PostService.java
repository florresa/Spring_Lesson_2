package service;

import com.google.gson.Gson;
import model.Post;
import org.springframework.stereotype.Service;
import repository.PostRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static controller.PostController.APPLICATION_JSON;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public boolean removeById(long id) {
        return repository.removeById(id);
    }

    public void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        if(data == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        response.getWriter().print(gson.toJson(data));
    }
}
