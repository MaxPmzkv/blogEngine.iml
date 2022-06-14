package main.api.response;

import lombok.Data;
import main.model.User;

@Data
public class CommentResponseUser {
    private int id;
    private String name;
    private String photo;

    public CommentResponseUser(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.photo = user.getPhoto();
    }


}
