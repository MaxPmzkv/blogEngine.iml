package main.controllers;

import main.api.request.CommentRequest;
import main.api.response.PostResponse;
import main.service.CommentService;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ApiCommentController {
    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public ApiCommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAuthority('user:write')")
//    public ResponseEntity addComment(@RequestBody CommentRequest commentRequest, Principal principal){
//        return commentService.addComment(commentRequest, principal);
//    }

    public Object addComment(@RequestBody CommentRequest commentRequest, Principal principal){
        commentService.addComment(commentRequest, principal);
        PostResponse postResponse = postService.getPostById(commentRequest.getPostId());
        return  postResponse;
    }
}
