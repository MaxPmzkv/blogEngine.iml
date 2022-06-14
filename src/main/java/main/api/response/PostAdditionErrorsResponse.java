package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import main.api.request.PostAdditionRequest;

@Data
public class PostAdditionErrorsResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;

    public PostAdditionErrorsResponse(PostAdditionRequest postAdditionRequest){
        this.text = postAdditionRequest.getText();
        this.title = postAdditionRequest.getTitle();


    }

}
