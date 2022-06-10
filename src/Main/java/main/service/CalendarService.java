package main.service;

import main.api.response.CalendarResponse;
import main.model.Post;
import main.model.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CalendarService {
    @Autowired
    PostRepository postRepository;


    public CalendarResponse getCalendar(String year) {
        Set<String> years = new TreeSet<>();
        Map<String, Integer> posts = new HashMap<>();
        List<Post> postList = postRepository.findAllPosts();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentYear = yearFormat.format(new Date());

        if(year.equals("none")){
            year = currentYear;
        }
        for(Post post : postList){
            String postYear = yearFormat.format(post.getTime());
            years.add(postYear);
            String dateForPosts = dateFormat.format(post.getTime());
            if(postYear.equals(year)){
                if(posts.containsKey(dateForPosts)){
                    posts.put(dateForPosts, posts.get(dateForPosts) + 1);
                }
                else {
                    posts.put(dateForPosts, 1);
                }
            }
        }

        return new CalendarResponse(years, posts);
    }

}
