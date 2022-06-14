package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.PostComment;

@Data
public class CommentResponse {

    private final int id;
    private final long timestamp;
    private final String text;
    @JsonProperty("user")
    private final CommentResponseUser commentResponseUser;

    public CommentResponse(PostComment postComment)
    {
        this.id = postComment.getId();
        this.timestamp = postComment.getTime().getTime() / 1000;
        this.text = postComment.getText();
        this.commentResponseUser = new CommentResponseUser(postComment.getUser());
    }


}
