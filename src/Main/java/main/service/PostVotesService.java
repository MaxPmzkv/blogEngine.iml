package main.service;

import main.api.request.LikeRequest;
import main.api.response.PostVoteResultResponse;
import main.model.User;
import main.model.repositories.PostVoteRepository;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class PostVotesService {
    @Autowired
    PostVoteRepository postVoteRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity addLike(LikeRequest likeRequest, Principal principal){
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if(postVoteRepository.findVotes(likeRequest.getPostId(), user.getId()) == null){
            postVoteRepository.insertVote(new Date(), (byte) 1, likeRequest.getPostId(), user.getId());
        }
        else if (postVoteRepository.findVotes(likeRequest.getPostId(), user.getId()).getValue() == 0){
            postVoteRepository.updateVote((byte) 1, likeRequest.getPostId(), user.getId());
        }
        else if (postVoteRepository.findVotes(likeRequest.getPostId(), user.getId()).getValue() == 1){
            return new ResponseEntity(new PostVoteResultResponse(false), HttpStatus.OK);
        }
        return new ResponseEntity(new PostVoteResultResponse( true), HttpStatus.OK);
    }

    public ResponseEntity addDislike(LikeRequest likeRequest, Principal principal){
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if(postVoteRepository.findVotes(likeRequest.getPostId(), user.getId()) == null){
            postVoteRepository.insertVote(new Date(), (byte) 0, likeRequest.getPostId(), user.getId());
        }
        else if (postVoteRepository.findVotes(likeRequest.getPostId(), user.getId()).getValue() == 1){
            postVoteRepository.updateVote((byte) 0, likeRequest.getPostId(), user.getId());
        }
        else if (postVoteRepository.findVotes(likeRequest.getPostId(), user.getId()).getValue() == 0){
            return new ResponseEntity(new PostVoteResultResponse(false), HttpStatus.OK);
        }
        return new ResponseEntity(new PostVoteResultResponse( true), HttpStatus.OK);
    }

}
