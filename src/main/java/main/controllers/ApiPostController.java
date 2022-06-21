package main.controllers;

import lombok.Data;
import main.api.request.LikeRequest;
import main.api.request.ModerationRequest;
import main.api.request.PostRequest;
import main.api.response.PostAdditionResponse;
import main.api.response.PostResponse;
import main.api.response.PostsResponse;
import main.service.ModerationService;
import main.service.PostService;
import main.service.PostVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Data
@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;
    private final PostVotesService postVotesService;
    private final ModerationService moderationService;

    @Autowired
    public ApiPostController(PostService postService, PostVotesService postVotesService, ModerationService moderationService) {
        this.postService = postService;
        this.postVotesService = postVotesService;
        this.moderationService = moderationService;
    }

    @GetMapping("")
    public ResponseEntity<PostsResponse> getPosts(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "recent") String mode){
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }

    @GetMapping("/search")
    public ResponseEntity<PostsResponse> getPostByQuery(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String query){
        return ResponseEntity.ok(postService.getPostsByQuery(offset, limit, query));
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostsResponse> getPostsByTag(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String tag) {

        return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String date) {

        return ResponseEntity.ok(postService.getPostsByDate(offset, limit, date));
    }

    @GetMapping("/{id}")
    public Object getPostsById(
            @PathVariable int id) {
        PostResponse postResponse = postService.getPostById(id);

        if (postResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return postResponse;
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsResponse> getMyPosts(@RequestParam(required = false, defaultValue = "0") int offset,
                                                    @RequestParam(required = false, defaultValue = "10") int limit,
                                                    @RequestParam(required = false, defaultValue = "") String status,
                                                    Principal principal){
        return ResponseEntity.ok(postService.getMyPosts(offset, limit, status, principal));
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostAdditionResponse> postNewPost(@RequestBody PostRequest postRequest, Principal principal){
        return ResponseEntity.ok(postService.postPost(postRequest, principal));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostAdditionResponse> updatePost(@PathVariable int id, @RequestBody PostRequest postRequest, Principal principal){
        return ResponseEntity.ok(postService.putPost(postRequest, id));
    }


    @PostMapping("/like")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity like(@RequestBody LikeRequest likeRequest,
                               Principal principal){
       return postVotesService.addLike(likeRequest, principal);
    }

    @PostMapping("/dislike")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity Dislike(@RequestBody LikeRequest likeRequest,
                               Principal principal){
        return postVotesService.addDislike(likeRequest, principal);

    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostsResponse> getPostsForModeration(@RequestParam(required = false, defaultValue = "0") int offset,
                                                               @RequestParam(required = false, defaultValue = "10") int limit,
                                                               @RequestParam(required = false, defaultValue = "") String status,
                                                               Principal principal){
        return ResponseEntity.ok(postService.getPostsForModeration(offset, limit, status, principal));
    }


}
