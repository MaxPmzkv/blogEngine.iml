package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PasswordResponseErrors {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String code;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String captcha;
}
