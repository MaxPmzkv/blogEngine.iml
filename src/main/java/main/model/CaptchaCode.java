package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "captcha_codes")
public class CaptchaCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "[time]", nullable = false)
    private LocalDateTime time;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String code;

    @Column(name="secret_code", nullable = false, columnDefinition = "TINYTEXT")
    private String secretCode;

}