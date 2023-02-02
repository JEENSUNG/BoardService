package boardService.board.repository.post;

import boardService.board.domain.post.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    //조회수 +1
    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.id = :id")
    void updateView(@Param("id") Long id);

    Page<Posts> findByTitleContaining(String keyword, Pageable pageable);

    @Modifying
    @Query("update Posts p set p.likeCount = p.likeCount + 1 where p.id = :id")
    void updateLikeCount(@Param("id") Long id);

    @Modifying
    @Query("update Posts p set p.disLikeCount = p.disLikeCount + 1 where p.id = :id")
    void updateDisLikeCount(@Param("id") Long id);

    @Modifying
    @Query(value = "update Posts p set p.disLikeCount = p.disLikeCount -1 where p.id = :id", nativeQuery = true)
    void updateDisLikeCountOf(@Param("id") Long id);

    @Modifying
    @Query(value = "update Posts p set p.LikeCount = p.LikeCount -1 where p.id = :id", nativeQuery = true)
    void updateLikeCountOf(@Param("id") Long id);
}