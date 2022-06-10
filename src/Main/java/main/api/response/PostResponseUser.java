package main.api.response;

import lombok.Data;
import main.model.Post;
import main.model.User;

@Data
public class PostResponseUser {
    private int id;
    private String name;

    public PostResponseUser(User user)
    {
        this.id = user.getId();
        this.name = user.getName();
    }

    public PostResponseUser(Post post) {
        this.id = post.getUser().getId();
        this.name = post.getUser().getName();

    }

}
