package main.model.repositories;

import main.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    @Query(value = "SELECT * FROM post_comments pc WHERE pc.post_id = :id", nativeQuery = true)
    List<PostComment> findComments(@Param("id") int id);

    @Query(value = "SELECT * FROM post_comments pc WHERE id = :id", nativeQuery = true)
    PostComment findCommentsById(@Param("id") int id);



    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO post_comments (parent_id, post_id, text, time, user_id)" +
                    " VALUES (:parent_id, :post_id, :text, :time, :user_id)",
            nativeQuery = true)
    void addComment(@Param("parent_id") Integer parent_id, @Param("post_id") int post_id, @Param("text") String text, @Param("time") Date time, @Param("user_id") int user_id);

    @Query(
            value = "SELECT LAST_INSERT_ID()",
            nativeQuery = true)
    int getLastId();

}
