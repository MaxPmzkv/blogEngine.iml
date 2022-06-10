package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostVoteResultResponse {

    @JsonProperty("result")
    private final boolean result;

    public PostVoteResultResponse(boolean result) {
        this.result = setResult(result);
    }

    private boolean setResult(Boolean result){
        return result;
    }
}
