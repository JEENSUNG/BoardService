package boardService.board.domain.bank;

import boardService.board.domain.user.TimeEntity;
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
public class BankTransaction extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String bankName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String toBankName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String username;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String toUsername;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false, unique = true)
    private String toAccount;

    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private int money;
}
