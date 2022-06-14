package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class RegistrationResponseError {

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("name")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("password")
    private String password;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("captcha")
    private String captcha;

    public RegistrationResponseError(AuthRegUser regInputError){
        this. email = regInputError.getEmail();
        this.name = regInputError.getName();
        this.password = regInputError.getPassword();
        this.captcha = regInputError.getCaptcha();
    }

}
