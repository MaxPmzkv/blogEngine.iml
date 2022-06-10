package main.api.response;

import lombok.Data;
import main.model.Post;
import main.model.PostVote;

import java.util.LinkedList;
import java.util.List;

@Data
public class PostResponse {
    private long id;
    private long timestamp;
    private boolean active;
    private PostResponseUser user;
    private String title;
    private String text;
    private long likeCount;
    private long dislikeCount;
    private long viewCount;
    private List<CommentResponse> comments;
    private List<String> tags;

    public PostResponse(List<CommentResponse> comments, Post post, List<String> tags) {
        this.id = post.getId();
        this.timestamp = post.getTime().getTime() / 1000;
        this.active = setActive(post.getIsActive());
        this.user = new PostResponseUser(post);
        this.title = post.getTitle();
        this.text = post.getText();
        this.likeCount = getLikeCount(post);
        this.dislikeCount = getDislikeCount(post);
        this.viewCount = post.getViewCount();
        this.comments = comments;
        this.tags = tags;
    }

    public long getLikeCount(Post post) {
        likeCount = 0;
        if (post.getLike() != null) {
            LinkedList<PostVote> likes = new LinkedList<>(post.getLike());
            for (PostVote like : likes) {

                if(like.getValue() == 1)
                {
                    likeCount++;
                }
            }
        }
        return likeCount;
    }
    public long getDislikeCount(Post post) {
        dislikeCount = 0;
        if (post.getLike() != null) {
            LinkedList<PostVote> likes = new LinkedList<>(post.getLike());
            for (PostVote like : likes) {
                if(like.getValue() == 0)
                {
                    dislikeCount++;
                }
            }
        }
        return dislikeCount;
    }
    public boolean setActive(byte a) {
        this.active = a == 1;

        return this.active;
    }

   }
