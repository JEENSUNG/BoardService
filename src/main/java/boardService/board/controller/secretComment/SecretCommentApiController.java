package boardService.board.controller.secretComment;

import boardService.board.domain.user.Role;
import boardService.board.dto.Result;
import boardService.board.dto.user.UserDto;
import boardService.board.dto.secret.SecretCommentDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.secretComment.SecretCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/secrets")
@RestController
//@PreAuthorize("hasAnyRole({'ROLE_VIP', 'ROLE_SOCIAL_VIP'})")
public class SecretCommentApiController {

    private final SecretCommentService secretCommentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<?> save(@PathVariable long id, @RequestBody SecretCommentDto.Request dto,
                                  @LoginUser UserDto.Response userDto, HttpSession httpSession){
        long commentId = secretCommentService.save(id, userDto.getNickname(), dto);
        UserDto.Response entity = secretCommentService.session(userDto.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok(commentId);
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
    public ResponseEntity<?> delete(@PathVariable long id, @LoginUser UserDto.Response userDto, HttpSession httpSession){
        secretCommentService.delete(id);
        UserDto.Response entity = secretCommentService.session(userDto.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok(id);
    }
}
