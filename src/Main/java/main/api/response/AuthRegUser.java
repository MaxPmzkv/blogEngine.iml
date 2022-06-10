package main.api.response;

import lombok.Data;

@Data
public class AuthRegUser {
    private String email;

    private String name;

    private String password;

    private String captcha;

    public Boolean checkingForErrors() {
        return email.isEmpty() && name.isEmpty() && password.isEmpty() && captcha.isEmpty();
    }
}
