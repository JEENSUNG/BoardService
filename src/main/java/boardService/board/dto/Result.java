package boardService.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    private T data;
    private boolean vip;

    public Result(T data, boolean vip){
        this.data = data;
        this.vip = vip;
    }
}
