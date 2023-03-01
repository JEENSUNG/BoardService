package boardService.board.controller.comment;

import boardService.board.domain.user.Role;
import boardService.board.dto.post.CommentDto;
import boardService.board.dto.util.Result;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<?> save(@PathVariable long id, @RequestBody @Valid CommentDto.Request dto,
                                  @LoginUser UserDto.Response userDto, HttpSession httpSession){
        Role now = userDto.getRole();
        long commentId = commentService.save(id, userDto.getNickname(), dto);
        UserDto.Response entity = commentService.session(userDto.getUsername());
        httpSession.setAttribute("user", entity);
        boolean isVip = commentService.check(userDto.getId(), now);
        return ResponseEntity.ok(new Result<>(commentId, isVip));
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentDto.Response> read(@PathVariable long id){
        return commentService.findAll(id);
    }

    @PutMapping({"/posts/{id}/comments/{id}"})
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid CommentDto.Request dto){
        commentService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/posts/{id}/comments/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, @LoginUser UserDto.Response userDto, HttpSession httpSession){
        commentService.delete(id);
        UserDto.Response entity = commentService.session(userDto.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok(id);
    }
}
