package main.model.repositories;

import main.model.Tag2Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2Post, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM tag2post WHERE tp.tag_id = :tagId AND tp.post_id = :postId")
    Tag2Post getTag2Post(int tagId, int postId);
}
