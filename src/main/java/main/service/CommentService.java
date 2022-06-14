package main.service;

import main.api.request.CommentRequest;
import main.api.response.CommentAdditionError;
import main.api.response.CommentAdditionResponse;
import main.api.response.CommentError;
import main.model.User;
import main.model.repositories.PostCommentRepository;
import main.model.repositories.PostRepository;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class CommentService {
    @Autowired
    PostCommentRepository postCommentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity addComment(CommentRequest commentRequest, Principal principal){
        if ((!(commentRequest.getParentId() == null) && postCommentRepository.findCommentsById(commentRequest.getParentId()) == null) || postRepository.findPostById(commentRequest.getPostId()) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(commentRequest.getText().length() < 8) {
            CommentAdditionError commentAdditionError = new CommentAdditionError();
            CommentError lengthError = new CommentError();
            lengthError.setText("Текст менее 8 символов");
            commentAdditionError.setResult(false);
            commentAdditionError.setErrors(lengthError);
            return new ResponseEntity<>(commentAdditionError, HttpStatus.OK);
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        postCommentRepository.addComment(commentRequest.getParentId(), commentRequest.getPostId(), commentRequest.getText(), new Date(), user.getId());

        int id = postCommentRepository.getLastId();
        CommentAdditionResponse commentAdditionResponse = new CommentAdditionResponse();
        return new ResponseEntity<>(commentAdditionResponse, HttpStatus.OK);
    }

    }
