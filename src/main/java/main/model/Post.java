package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

    @Data
    @Entity
    @Table(name = "posts")
    public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "is_active", nullable = false)
        private byte isActive;

        @Enumerated(EnumType.STRING)
        @Column(name = "moderation_status", columnDefinition = "enum('NEW','ACCEPTED','DECLINED') default 'NEW'", length = 8, nullable = false)
        private ModerationStatus moderationStatus = ModerationStatus.NEW;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "moderator_id", referencedColumnName = "id")
        private User moderator;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id", nullable=false)
        private User user;

        @Column(name = "time", nullable = false)
        private Date time;

        @Column(nullable = false)
        private String title;

        @Column(length = 65535, columnDefinition = "Text", nullable = false)
        private String text;

        @Column(name = "view_count", nullable = false)
        private int viewCount;

        @OneToMany(mappedBy="post")
        private List<Tag2Post> tags;

        @OneToMany(mappedBy="post")
        private List<PostVote> like;

        @OneToMany(mappedBy="post")
        private List<PostComment> comments;

    }
