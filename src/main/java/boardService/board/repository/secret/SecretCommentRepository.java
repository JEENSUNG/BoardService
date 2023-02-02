package boardService.board.repository.secret;

import boardService.board.domain.secret.SecretComment;
import boardService.board.domain.secret.SecretPosts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecretCommentRepository extends JpaRepository<SecretComment, Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<SecretComment> getCommentByPostsOrderById(SecretPosts secretPosts);
}