package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SettingsRequest {
    @JsonProperty("MULTIUSER_MODE")
    private boolean multiUserMode;
    @JsonProperty("POST_PRMODERATION")
    private boolean postPreModeration;
    @JsonProperty("STATISTIC_IS_PUBLIC")
    private boolean statisticsIsPublic;

}
