package boardService.board.repository.post;

import boardService.board.domain.post.PostsSearch;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<PostsSearch, Long> {
    Optional<PostsSearch> findByWords(String keyword);

    List<PostsSearch> findTop10By(Sort sort);
}
