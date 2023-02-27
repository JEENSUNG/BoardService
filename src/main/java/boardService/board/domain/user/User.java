package boardService.board.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username; // 아이디

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int point;

    @Column(nullable = false)
    private boolean isBank;

    /* 회원정보 수정 */
    public void modify(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public void setPoint(int point){
        this.point = point;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void setIsBank(boolean isBank){
        this.isBank = isBank;
    }

    /* 소셜로그인시 이미 등록된 회원이라면 수정날짜만 업데이트해줘서
     * 기존 데이터를 보존하도록 예외처리 */
    public User updateModifiedDate() {
        this.onPreUpdate();
        return this;
    }

    public String getRoleValue() {
        return this.role.getValue();
    }
}
