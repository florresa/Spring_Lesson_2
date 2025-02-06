package servlet;

import controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("java");
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            final var path = request.getRequestURI();
            final var method = request.getMethod();

            if (method.equals(GET) && path.equals("/api/posts")) {
                controller.all(response);
                return;
            }
            if (method.equals(GET) && path.matches("/api/posts/\\d+")) {
                var id = Long.parseLong(path.substring(path.lastIndexOf("/")).replace("/",""));
                controller.getById(id, response);
                return ;
            }
            if (method.equals(POST) && path.equals("/api/posts")) {
                controller.save(request.getReader(), response);
                return;
            }
            if (method.equals(DELETE) && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")).replace("/",""));
                controller.removeById(id, response);
                return;
            }
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}