package main.service;

import main.api.request.PostAdditionRequest;
import main.api.request.PostRequest;
import main.api.response.*;
import main.model.*;
import main.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Tag2PostRepository tag2PostRepository;

    @Autowired
    TagRepository tagRepository;

    public PostsResponse getPosts(int offset, int limit, String mode) {
        Pageable pageable = PageRequest.of(offset / limit, limit);

        Page<Post> postsPage;

        switch (mode) {
            case "popular":
                postsPage = postRepository.findAllPostsByCommentsDesc(pageable);
                break;
            case "best":
                postsPage = postRepository.findAllPostsByVotesDesc(pageable);
                break;
            case "early":
                postsPage = postRepository.findAllPostsByTime(pageable);
                break;
            default:
                postsPage = postRepository.findAllPostsByTimeDesc(pageable);
                break;
        }
        return createPostsResponse(postsPage, postRepository.findAllPosts().size());
    }

    public PostsResponse getPostsByTag(int offset, int limit, String tag) {
        Pageable pageable;
        pageable = PageRequest.of(offset / limit, limit);
        Page<Post> pageByTag = postRepository.getPostsByTag(tag, pageable);
        return createPostsResponse(pageByTag, (int) pageByTag.getTotalElements());
    }

    private PostsResponse createPostsResponse(Page<Post> pageOfTags, int size) {
        List<CreatePostResponse> postResponseList = new ArrayList<>();
        for (Post p : pageOfTags) {
            postResponseList.add(new CreatePostResponse(p));
        }
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setPosts(postResponseList);
        postsResponse.setCount(size);
        return postsResponse;
    }


    public PostsResponse getPostsByQuery(int offset, int limit, String query) {
        Pageable pageable;
        pageable = PageRequest.of(offset / limit, limit);
        Page<Post> pageByQuery = postRepository.getPostByQuery(query, pageable);
        return createPostsResponse(pageByQuery, (int) pageByQuery.getTotalElements());

    }

    public PostsResponse getPostsByDate(int offset, int limit, String date) {
        Pageable pageable;
        pageable = PageRequest.of(offset / limit, limit);
        Page<Post> pageByQuery = postRepository.findAllPostsByDate(date, pageable);
        return createPostsResponse(pageByQuery, (int) pageByQuery.getTotalElements());
    }

    public PostResponse getPostById(int id) {
        List<PostComment> postComments = postCommentRepository.findComments(id);
        List<String> tags = tagRepository.getTagsByPostId(id);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (PostComment postComment : postComments) {
            commentResponseList.add(new CommentResponse(postComment));
        }
        Post post = postRepository.findPostById(id);

        if (post == null) {
            return null;
        } else {
            post.setViewCount(post.getViewCount() + 1);
            postRepository.save(post);
        }
        return new PostResponse(commentResponseList, post, tags);
    }

    public PostsResponse getMyPosts(int offset, int limit, String status, Principal principal) {
        Pageable pageable;
        pageable = PageRequest.of(offset / limit, limit);
        switch (status) {
            case "inactive": {
                Page<Post> MyPosts = postRepository.findMyInActivePosts(pageable, principal.getName());
                return createPostsResponse(MyPosts, (int) MyPosts.getTotalElements());
            }
            case "pending": {
                Page<Post> MyPosts = postRepository.findMyActivePosts("NEW", principal.getName(), pageable);
                return createPostsResponse(MyPosts, (int) MyPosts.getTotalElements());
            }
            case "declined": {
                Page<Post> MyPosts = postRepository.findMyActivePosts("DECLINED", principal.getName(), pageable);
                return createPostsResponse(MyPosts, (int) MyPosts.getTotalElements());
            }
            case "published": {
                Page<Post> MyPosts = postRepository.findMyActivePosts("ACCEPTED", principal.getName(), pageable);
                return createPostsResponse(MyPosts, (int) MyPosts.getTotalElements());
            }
        }
        return null;
    }

    public PostAdditionRequest checkAdditionRequest(String title, String text) {
        PostAdditionRequest request = new PostAdditionRequest();
        if (title.trim().isEmpty() || title.length() < 3) {
            request.setTitle("Заголовок не установлен");
        } else {
            request.setTitle("");
        }

        if (text.trim().isEmpty() || text.length() < 50) {
            request.setText("Текст публикации слишком короткий");
        } else {
            request.setText("");
        }
        return request;
    }

    @Transactional
    public PostAdditionResponse postPost(PostRequest postRequest, Principal principal) {
        String text = postRequest.getText();
        String title = postRequest.getTitle();
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        PostAdditionRequest postAdditionRequest = checkAdditionRequest(title, text);
        PostAdditionResponse postAdditionResponse = new PostAdditionResponse(postAdditionRequest);
        if (postAdditionRequest.hasNoErrors()) {
            Post post = new Post();
            post.setIsActive(postRequest.getActive());
            post.setText(postRequest.getText());
            post.setTitle(postRequest.getTitle());
            post.setUser(user);
            post.setViewCount(0);
            Date now = new Date();
            Date postDate = new Date(postRequest.getTimestamp());
            if (postDate.before(now)) {
                post.setTime(now);
            } else {
                post.setTime(postDate);
            }
            post.setModerationStatus(ModerationStatus.NEW);
            post = postRepository.save(post);
            List<String> tags = postRequest.getTags();
            for (String t : tags
            ) {
                Tag tag = new Tag();
                tag.setName(t);
                Tag2Post tags2Post = new Tag2Post();
                tags2Post.setPostId(post);
                tags2Post.setTagId(tagRepository.save(tag));
                tag2PostRepository.save(tags2Post);
            }

        }
        return postAdditionResponse;
    }

    public PostAdditionResponse putPost(PostRequest postRequest, int id) {
        String text = postRequest.getText();
        String title = postRequest.getTitle();
        PostAdditionRequest postAdditionRequest = checkAdditionRequest(title, text);
        PostAdditionResponse postAdditionResponse = new PostAdditionResponse(postAdditionRequest);
        if (postAdditionRequest.hasNoErrors()) {
            Date now = new Date();
            Date postDate = new Date(postRequest.getTimestamp());
            if (postDate.before(now)) {
                postDate = now;
            }
            postRepository.updatePost(postDate
                    , postRequest.getActive(), postRequest.getTitle(), postRequest.getText(), id);
            List<String> oldTags = tagRepository.getTagsByPostId(id);

            Post post = postRepository.findPostById(id);

            List<String> tags = postRequest.getTags();

            for (String t : tags) {

                if (!oldTags.contains(t)) {
                    Tag tag = new Tag();
                    tag.setName(t);
                    Tag2Post tags2Post = new Tag2Post();
                    tags2Post.setPostId(post);
                    tags2Post.setTagId(tagRepository.save(tag));
                    tag2PostRepository.save(tags2Post);
                } else {
                    oldTags.remove(t);
                }
            }
            if (oldTags.size() > 0) {
                for (String t : oldTags) {
                    tag2PostRepository.delete(tag2PostRepository.getTag2Post(tagRepository.getByName(t), id));
                }
            }
        }
        return postAdditionResponse;
    }

    public PostsResponse getPostsForModeration(int offset, int limit, String status, Principal principal) {
        Pageable pageable;
        pageable = PageRequest.of(offset / limit, limit);
        Page<Post> pageModerate;
        if (status.equals("new")) {
            pageModerate = postRepository.getPostByModerationStatus(status, pageable);
        } else {
            User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            pageModerate = postRepository.getMyPostsByModerationStatus(status, user.getId(), pageable);
        }
        return createPostsResponse(pageModerate, (int) pageModerate.getTotalElements());
    }


}

