package boardService.board.repository.secret;

import boardService.board.domain.user.User;
import boardService.board.domain.secret.SecretLikes;
import boardService.board.domain.secret.SecretPosts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretLikesRepository extends JpaRepository<SecretLikes, Long> {
    SecretLikes findByUser(User user);

    Optional<SecretLikes> findByUserAndPosts(User user, SecretPosts secretPosts);
}
