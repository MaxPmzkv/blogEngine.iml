package main.service;

import main.api.response.StatisticsResponse;
import main.model.Post;
import main.model.PostVote;
import main.model.User;
import main.model.repositories.GlobalSettingsRepository;
import main.model.repositories.PostRepository;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GlobalSettingsRepository globalSettingsRepository;

    public StatisticsResponse getMyStats(Principal principal){
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        List<Post> myPosts = postRepository.findMyPosts("ACCEPTED", principal.getName());

        if(myPosts.size() == 0){
            statisticsResponse.setPostsCount(0);
            statisticsResponse.setLikesCount(0);
            statisticsResponse.setDislikesCount(0);
            statisticsResponse.setViewsCount(0);
            statisticsResponse.setFirstPublication(0);

            return statisticsResponse;
        }
        long postsCount = myPosts.size();
        long likesCount = 0;
        long dislikesCount = 0;
        long viewsCount = 0;
        long firstPublication = myPosts.get(0).getTime().getTime() / 1000;

        for(Post p : myPosts){
            likesCount += getLikes(p);
            dislikesCount += getDislikes(p);
            viewsCount += p.getViewCount();
        }
        statisticsResponse.setPostsCount(postsCount);
        statisticsResponse.setLikesCount(likesCount);
        statisticsResponse.setDislikesCount(dislikesCount);
        statisticsResponse.setViewsCount(viewsCount);
        statisticsResponse.setFirstPublication(firstPublication);

        return statisticsResponse;
    }


    public ResponseEntity getAllStats(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if(user.getIsModerator() == 0 && globalSettingsRepository.findGlobalSettings("STATISTICS_IS_PUBLIC").getValue().equals("NO")){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        List<Post> posts = postRepository.findAllPosts();
        long postsCount = posts.size();
        long likesCount = 0;
        long dislikesCount = 0;
        long viewsCount = 0;
        long firstPublication = posts.get(0).getTime().getTime() / 1000;

        for(Post p : posts){
            likesCount += getLikes(p);
            dislikesCount += getDislikes(p);
            viewsCount += p.getViewCount();
        }

        statisticsResponse.setPostsCount(postsCount);
        statisticsResponse.setLikesCount(likesCount);
        statisticsResponse.setDislikesCount(dislikesCount);
        statisticsResponse.setViewsCount(viewsCount);
        statisticsResponse.setFirstPublication(firstPublication);

        return new ResponseEntity(statisticsResponse, HttpStatus.OK);
    }

    private long getLikes(Post post){
        long likesCount = 0;
        LinkedList<PostVote> likes = new LinkedList<>(post.getLike());
        if(!(post.getLike() == null)){
            for(PostVote l : likes){
                if(l.getValue() == 1){
                    likesCount++;
                }
            }
        }
        return likesCount;
    }

    private long getDislikes(Post post){
        long DislikesCount = 0;
        LinkedList<PostVote> likes = new LinkedList<>(post.getLike());
        if(!(post.getLike() == null)){
            for(PostVote l : likes){
                if(l.getValue() == 0){
                    DislikesCount++;
                }
            }
        }
        return DislikesCount;
    }

}
