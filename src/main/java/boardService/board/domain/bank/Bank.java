package boardService.board.domain.bank;

import boardService.board.domain.user.Role;
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
public class Bank extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankName bankName;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int money;

    public void setBankName(BankName bankName){
        this.bankName = bankName;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public void setMoney(int money){
        this.money = money;
    }
}
