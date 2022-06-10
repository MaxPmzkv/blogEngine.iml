package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModerationResultResponse {
    @JsonProperty("result")
    private final Boolean result;

    public ModerationResultResponse(Boolean result) {
        this.result = setResult(result);
    }
    public Boolean setResult(Boolean result) {
        return result;
    }
}
