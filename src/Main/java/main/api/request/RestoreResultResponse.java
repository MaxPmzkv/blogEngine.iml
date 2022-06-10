package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestoreResultResponse {
    @JsonProperty("result")
    private final Boolean result;

    public RestoreResultResponse(Boolean result) {
        this.result = setResult(result);
    }

    public Boolean setResult(Boolean result) {
        return result;
    }
}
