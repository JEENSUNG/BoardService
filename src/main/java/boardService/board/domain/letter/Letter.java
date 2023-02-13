package boardService.board.domain.letter;

import boardService.board.domain.user.TimeEntity;
import boardService.board.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Letter extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(nullable = false)
    private String sendUsername;

    @Column(nullable = false)
    private String takenUsername;

    private long toUser;

    private long fromUser;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}
