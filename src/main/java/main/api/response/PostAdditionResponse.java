package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.api.request.PostAdditionRequest;

@Data
@NoArgsConstructor
public class PostAdditionResponse {

    private boolean result;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private PostAdditionErrorsResponse errors;

    public PostAdditionResponse(PostAdditionRequest requestPost) {
        if (requestPost.getTitle().isEmpty() &&
                requestPost.getText().isEmpty()) {
            this.result = true;
        } else {
            this.result = false;
            this.errors = new PostAdditionErrorsResponse(requestPost);
        }
    }


}
