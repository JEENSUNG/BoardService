package boardService.board.controller.secret;

import boardService.board.dto.Result;
import boardService.board.dto.secret.SecretPostsDto;
import boardService.board.dto.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.secret.SecretPostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secrets")
public class SecretPostsApiController {
    private final SecretPostsService secretPostsService;

    @PostMapping("/posts")
    public ResponseEntity<?> save(@RequestBody SecretPostsDto.Request dto, @LoginUser UserDto.Response user) {
        long id = secretPostsService.save(dto, user.getNickname());
        boolean isVip = secretPostsService.check(user.getId());
        return ResponseEntity.ok(new Result<>(id, isVip));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> read(@PathVariable long id){
        return ResponseEntity.ok(secretPostsService.findById(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody SecretPostsDto.Request dto){
        secretPostsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        secretPostsService.delete(id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> like(@PathVariable long id, @LoginUser UserDto.Response dto){
        secretPostsService.like(id, dto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/posts/{id}/disLike")
    public ResponseEntity<?> disLike(@PathVariable long id, @LoginUser UserDto.Response dto){
        secretPostsService.disLike(id, dto);
        return ResponseEntity.ok(id);
    }
}
