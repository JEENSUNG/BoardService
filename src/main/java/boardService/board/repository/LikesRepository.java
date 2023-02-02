package boardService.board.repository;

import boardService.board.domain.Likes;
import boardService.board.domain.Posts;
import boardService.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByUser(User user);

    Optional<Likes> findByUserAndPosts(User user, Posts posts);
}
