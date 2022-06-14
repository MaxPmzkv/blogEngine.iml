package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_votes")
public class PostVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable=false)
    private Post post;

    @Column
    private LocalDateTime time;

    @Column(name = "[value]", nullable = false)
    private byte value;
}
