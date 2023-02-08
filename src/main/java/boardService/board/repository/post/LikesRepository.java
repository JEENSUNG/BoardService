package boardService.board.repository.post;

import boardService.board.domain.post.Likes;
import boardService.board.domain.post.Posts;
import boardService.board.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByUser(User user);

    Optional<Likes> findByUserAndPosts(User user, Posts posts);
}
