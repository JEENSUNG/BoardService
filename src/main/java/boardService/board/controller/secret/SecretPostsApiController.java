package boardService.board.controller.secret;

import boardService.board.dto.Result;
import boardService.board.dto.secret.SecretPostsDto;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.secret.SecretPostsService;
import boardService.board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secrets")
//@PreAuthorize("hasAnyRole({'ROLE_VIP', 'ROLE_SOCIAL_VIP'})")
public class SecretPostsApiController {
    private final SecretPostsService secretPostsService;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<?> check(@RequestBody UserDto.Response user){
        boolean isVip = userService.check(user.getId());
        return ResponseEntity.ok(new Result<>(isVip));
    }

    @PostMapping("/api/posts")
    public ResponseEntity<?> save(@RequestBody SecretPostsDto.Request dto, @LoginUser UserDto.Response user, HttpSession httpSession) {
        long id = secretPostsService.save(dto, user.getNickname());
        boolean isVip = secretPostsService.check(user.getId(), user.getRole());
        UserDto.Response entity = secretPostsService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok(new Result<>(id, isVip));
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<?> read(@PathVariable long id){
        return ResponseEntity.ok(secretPostsService.findById(id));
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody SecretPostsDto.Request dto){
        secretPostsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, @LoginUser UserDto.Response user, HttpSession httpSession){
        secretPostsService.delete(id);
        UserDto.Response entity = secretPostsService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/api/posts/{id}/like")
    public ResponseEntity<?> like(@PathVariable long id, @LoginUser UserDto.Response dto){
        secretPostsService.like(id, dto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/api/posts/{id}/disLike")
    public ResponseEntity<?> disLike(@PathVariable long id, @LoginUser UserDto.Response dto){
        secretPostsService.disLike(id, dto);
        return ResponseEntity.ok(id);
    }
}
