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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    private BankName bankName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    private BankName toBankName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String username;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String toUsername;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String toAccount;

    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private int money;

    public void setBankName(BankName bankName){
        this.bankName = bankName;
    }

    public void setToBankName(BankName toBankName){
        this.toBankName = toBankName;
    }
}
