package main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @Column(name = "is_moderator", nullable = false)
    private byte isModerator;

    @JsonIgnore
    @Column(name = "reg_time", nullable = false)
    private LocalDateTime registrationTime;

    @Column(name = "[name]", nullable = false)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    private String code;

    @Column(length = 65535, columnDefinition = "Text")
    private String photo;

    public Role getRole(){
        return isModerator == 1 ? Role.MODERATOR : Role.USER;
    }







}
