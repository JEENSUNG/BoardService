package boardService.board.controller.post;

import boardService.board.domain.user.Role;
import boardService.board.dto.post.PostsDto;
import boardService.board.dto.util.Result;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.post.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/posts")
    public ResponseEntity<?> save(@RequestBody @Valid PostsDto.Request dto, @LoginUser UserDto.Response user, HttpSession httpSession) {
        Role now = user.getRole();
        long id = postsService.save(dto, user.getNickname());
        UserDto.Response entity = postsService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        boolean isVip = postsService.check(user.getId(), now);
        return ResponseEntity.ok(new Result<>(id, isVip));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> read(@PathVariable long id){
        return ResponseEntity.ok(postsService.findById(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid PostsDto.Request dto){
        postsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, @LoginUser UserDto.Response user, HttpSession httpSession){
        postsService.delete(id);
        UserDto.Response entity = postsService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> like(@PathVariable long id, @LoginUser UserDto.Response dto){
        postsService.like(id, dto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/posts/{id}/disLike")
    public ResponseEntity<?> disLike(@PathVariable long id, @LoginUser UserDto.Response dto){
        postsService.disLike(id, dto);
        return ResponseEntity.ok(id);
    }

}
