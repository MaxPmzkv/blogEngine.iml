package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegistrationRequest {
    @JsonProperty("e_mail")
    private String email;
    private String password;
    private String name;
    private String captcha;
    @JsonProperty("captcha_secret")
    private String secretCaptcha;

    public Boolean checkingForErrors() {
        return email.isEmpty() && name.isEmpty() && password.isEmpty() && captcha.isEmpty();
    }
}
