package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class RegistrationResponse {
    @JsonProperty("result")
    private Boolean result;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private RegistrationResponseError errors;

    public RegistrationResponse(AuthRegUser authRegUser){
        if(authRegUser.getEmail().isEmpty() &&
        authRegUser.getPassword().isEmpty() &&
        authRegUser.getCaptcha().isEmpty() &&
        authRegUser.getName().isEmpty()){
            this.result = true;
        }
        else {
            this.result = false;
            this.errors = new RegistrationResponseError(authRegUser);
        }
    }

}
