package main.api.request;

import lombok.Data;

@Data
public class PostAdditionRequest {
    private String title;
    private String text;

    public boolean hasNoErrors(){
        return title.isEmpty() && text.isEmpty();
    }


}
