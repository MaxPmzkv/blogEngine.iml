package main.model.repositories;

import main.api.response.GeneralPostsAmountByTag;
import main.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

   @Query(value = "SELECT t.name as name, COUNT(t.id) as count FROM tags t JOIN tag2post tp ON tp.tag_id = t.id JOIN posts p on p.id = tp.post_id WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' GROUP BY tp.tag_id ORDER BY count DESC", nativeQuery = true)
    List<GeneralPostsAmountByTag> getTags();

   @Query(value = "SELECT t.name FROM tags t JOIN tag2post tp on tp.tag_id = t.id WHERE tp.post_id = :post_id", nativeQuery = true)
   List<String> getTagsByPostId(@Param("post_id") int postId);

    @Query(nativeQuery = true, value = "SELECT t.id FROM tags t WHERE t.name = :name")
    Integer getByName(@Param("name") String name);
}
