package boardService.board.repository.letter;

import boardService.board.domain.letter.Letter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    Page<Letter> findAllByToUser(long id, Pageable pageable);

    Page<Letter> findAllByFromUser(long id, Pageable pageable);
}
