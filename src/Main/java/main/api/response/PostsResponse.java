package main.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostsResponse {
    private long count;
    private List<CreatePostResponse> posts;

//    public PostsResponse(long count, List<Post> posts) {
//        this.count = count;
//        this.posts = posts.stream().map(CreatePostResponse::new).collect(Collectors.toList());
//    }
}
