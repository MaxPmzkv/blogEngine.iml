package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ProfileEditingErrors {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String photo;


}
