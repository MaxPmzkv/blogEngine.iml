package main.api.response;

import lombok.Data;

@Data
public class AuthCaptchaResponse {
    private String secret;
    private String image;
}
