package main.service;

import main.api.request.ModerationRequest;
import main.api.response.ModerationResultResponse;
import main.model.User;
import main.model.repositories.PostRepository;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ModerationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public ResponseEntity<ModerationResultResponse> moderatePost(ModerationRequest moderationRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if (user.getIsModerator() == 0){
            return new ResponseEntity<>(new ModerationResultResponse(false), HttpStatus.OK);
        }
        String moderationStatus;
        if (moderationRequest.getDecision().equals("accept")) {
            moderationStatus = "ACCEPTED";
        } else moderationStatus = "DECLINED";
        postRepository.updatePostModerationStatus(moderationStatus, moderationRequest.getPostId(), user.getId());
        if (postRepository.findPostById(moderationRequest.getPostId()).getModerationStatus().equals("NEW")){
            return new ResponseEntity<>(new ModerationResultResponse(false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ModerationResultResponse(true), HttpStatus.OK);
    }
}
