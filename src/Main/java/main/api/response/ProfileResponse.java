package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfileResponse {
    @JsonProperty("result")
    private boolean result;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ProfileEditingErrors errors;


}
