package boardService.board.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostsSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false, unique = true)
    public String words;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int searchCount;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int ranking;

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setSearchCount(int searchCount){
        this.searchCount = searchCount;
    }
}
