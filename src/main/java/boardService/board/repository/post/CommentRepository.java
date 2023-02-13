package boardService.board.repository.post;

import boardService.board.domain.post.Comment;
import boardService.board.domain.post.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}