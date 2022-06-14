package main.model.repositories;

import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW() ORDER BY (SELECT count(*) FROM post_comments c WHERE c.post_id = p.id) DESC", nativeQuery = true)
    Page<Post>findAllPostsByCommentsDesc(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW() ORDER BY (SELECT sum(value) FROM post_votes c WHERE c.post_id = p.id) DESC", nativeQuery = true)
    Page<Post>findAllPostsByVotesDesc(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW() ORDER BY p.time", nativeQuery = true)
    Page<Post>findAllPostsByTime(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW() ORDER by p.time DESC", nativeQuery = true)
    Page<Post>findAllPostsByTimeDesc(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED'  ORDER BY p.time", nativeQuery = true)
    List<Post> findAllPosts();

    @Query(value = "SELECT * FROM posts p JOIN tag2post tp ON tp.post_id = p.id JOIN tags t ON t.id = tp.tag_id WHERE t.name = :tag AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED' order by p.time", nativeQuery = true)
    Page<Post> getPostsByTag(@Param("tag") String tag, Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.text like %:query% AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED'", nativeQuery = true)
    Page<Post> getPostByQuery(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.time LIKE :date% AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY p.time", nativeQuery = true)
    Page<Post> findAllPostsByDate(@Param("date") String date, Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.id = :id", nativeQuery = true)
    Post findPostById(@Param("id") int id);


    @Query(value = "SELECT COUNT(*) FROM posts p JOIN users u ON u.id = p.user_id WHERE u.email = :email AND p.is_active = 1 AND p.moderation_status = 'NEW'", nativeQuery = true)
    int findAllModeratedPosts(String email);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = :status AND p.`time` < NOW() ORDER BY p.time", nativeQuery = true)
    Page<Post> getPostByModerationStatus(@Param("status") String status, Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.moderator_id = :id AND p.is_active = 1 AND p.moderation_status = :status AND p.`time` < NOW() ORDER BY p.time", nativeQuery = true)
    Page<Post> getMyPostsByModerationStatus(@Param("status") String status, @Param("id") int id, Pageable pageable);

    @Query(value = "SELECT * FROM posts p JOIN users u ON p.user_id WHERE u.email = :email AND p.is_active = 0 AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    Page<Post> findMyInActivePosts(Pageable pageable, @Param("email") String email);

    @Query(value = "SELECT * FROM posts p JOIN users u ON u.id = p.user_id WHERE u.email = :email AND p.is_active = 1 AND p.moderation_status = :status AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    Page<Post> findMyActivePosts(@Param("status") String status, @Param("email") String email, Pageable pageable);

    @Query(value = "SELECT * FROM posts p JOIN users u ON u.id = p.user_id WHERE u.email = :email AND p.is_active = 1 AND p.moderation_status = :status AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    List<Post> findMyPosts(@Param ("status") String status, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO posts (is_active, moderation_status, text, time, title, view_count, user_id) VALUES (:active, 'NEW', :text, :date, :title, 0, :user))", nativeQuery = true)
    void insertNewPost(@Param("active") byte active, @Param("text") String test, @Param("date") Date date,
                       @Param("title") String title, @Param("user") int userId);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE posts p SET p.is_active = :active, p.moderation_status = 'NEW', p.text = :text, p.title = :title, p.time = :date WHERE p.id = :id", nativeQuery = true)
    void updatePost(@Param("date") Date date, @Param("active") byte active, @Param("title") String title, @Param("text") String text, @Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts p SET  p.moderation_status = :moderation_status, p.moderator_id = :moderator_id WHERE p.id = :id",
            nativeQuery = true)
    void updatePostModerationStatus(@Param("moderation_status") String moderation_status, @Param("id") int id, @Param("moderator_id") int moderator_id);



}
