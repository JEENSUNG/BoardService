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

    @Column(nullable = false)
    private long toUser;

    @Column(nullable = false)
    private long fromUser;

    private long pageNum;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public void setToUser(long toUser){
        this.toUser = toUser;
    }
    public void setFromUser(long fromUser){
        this.fromUser = fromUser;
    }
}
