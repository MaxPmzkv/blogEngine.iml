package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PasswordResponse {
    @JsonProperty("result")
    private boolean result;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("error")
    private PasswordResponseErrors passwordResponseError;

}
