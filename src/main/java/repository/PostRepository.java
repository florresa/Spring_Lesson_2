package repository;

import model.Post;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Stub
public class PostRepository {
    private Map<Long, Post> postMap = new ConcurrentHashMap<>();
    long count = 1;

    public List<Post> all() {
        return postMap.values().stream().collect(Collectors.toList());
    }

    public Post getById(long id) {
        return postMap.get(id);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            AtomicLong newID = new AtomicLong();
            newID.set(count);
            count++;
            post.setId(newID.get());
            return postMap.put(newID.get(), post);
        } else {
            return postMap.replace(post.getId(),post);
        }
    }

    public boolean removeById(long id) {
        var item = postMap.get(id);
        if (item != null) {
            postMap.remove(id);
            return true;
        }
        return false;
    }
}
