package boardService.board.controller.post;

import boardService.board.dto.post.CommentDto;
import boardService.board.dto.Result;
import boardService.board.dto.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.post.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<?> save(@PathVariable long id, @RequestBody CommentDto.Request dto,
                                  @LoginUser UserDto.Response userDto){
        long commentId = commentService.save(id, userDto.getNickname(), dto);
        boolean isVip = commentService.check(userDto.getId());
        return ResponseEntity.ok(new Result<>(commentId, isVip));
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentDto.Response> read(@PathVariable long id){
        return commentService.findAll(id);
    }

    @PutMapping({"/posts/{id}/comments/{id}"})
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody CommentDto.Request dto){
        commentService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/posts/{id}/comments/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
