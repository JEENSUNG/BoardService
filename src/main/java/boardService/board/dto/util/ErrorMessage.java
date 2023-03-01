package boardService.board.dto.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage<T> {
    private T data;
    private String message;

    public ErrorMessage(T data, String message){
        this.data = data;
        this.message = message;
    }

    public ErrorMessage(T data){
        this.data = data;
    }
}