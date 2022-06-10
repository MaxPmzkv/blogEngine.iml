package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettingsResponse {
    @JsonProperty("MULTIUSER_MODE")
    private boolean multiuserMode;
    @JsonProperty("POST_PREMODITATION")
    private boolean postpremoderation;
    @JsonProperty("STATISTIC_IS_PUBLIC")
    private boolean statisticIsPublic;
}
