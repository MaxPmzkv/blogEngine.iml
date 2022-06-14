package main.model.repositories;

import main.model.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM post_votes pv where pv.post_id = :post_id AND pv.user_id = :user_id")
    PostVote findVotes(@Param("post_id") int postId, @Param("user_id") int userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO post_votes (time, value, post_id, user_id)" +
    " VALUES (:time, :value, :post_id, :user_id)")
    void insertVote(@Param("time")Date time, @Param("value") byte value,
                    @Param("post_id") int postId, @Param("user_id") int userId);


    @Modifying
    @Transactional
    @Query(
            value = "UPDATE post_votes pv SET  pv.value = :value WHERE pv.post_id = :post_id AND pv.user_id = :user_id",
            nativeQuery = true)
    void updateVote(@Param("value") byte value, @Param("post_id") int postId, @Param("user_id") int userId);

}
