package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class TagsResponse {

    @JsonProperty("tags")
    private List<TagResponse> tags;

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }
}
