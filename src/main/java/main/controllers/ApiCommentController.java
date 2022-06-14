package main.controllers;

import main.api.request.CommentRequest;
import main.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public ApiCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity addComment(@RequestBody CommentRequest commentRequest, Principal principal){
        return commentService.addComment(commentRequest, principal);
    }
}
