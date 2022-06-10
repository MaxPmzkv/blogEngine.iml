package main.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tag2post")
public class Tag2Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public Post getPostId() {
        return post;
    }

    public void setPostId(Post postId) {
        this.post = postId;
    }
    public Tag getTagId() {
        return tag;
    }

    public void setTagId(Tag tagId) {
        this.tag = tagId;
    }
}