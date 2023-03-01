package boardService.board.dto.util;

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

    public Result(T data){
        this.data = data;
    }
}
