package boardService.board.controller.secret;

import boardService.board.dto.Result;
import boardService.board.dto.UserDto;
import boardService.board.dto.post.CommentDto;
import boardService.board.dto.secret.SecretCommentDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.post.CommentService;
import boardService.board.service.secret.SecretCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/secrets")
@RestController
public class SecretCommentApiController {

    private final SecretCommentService secretCommentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<?> save(@PathVariable long id, @RequestBody SecretCommentDto.Request dto,
                                  @LoginUser UserDto.Response userDto){
        long commentId = secretCommentService.save(id, userDto.getNickname(), dto);
        boolean isVip = secretCommentService.check(userDto.getId());
        return ResponseEntity.ok(new Result<>(commentId, isVip));
    }

    @GetMapping("/posts/{id}/comments")
    public List<SecretCommentDto.Response> read(@PathVariable long id){
        return secretCommentService.findAll(id);
    }

    @PutMapping({"/posts/{id}/comments/{id}"})
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody SecretCommentDto.Request dto){
        secretCommentService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/posts/{id}/comments/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        secretCommentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
