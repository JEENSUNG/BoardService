package boardService.board.controller.letter;

import boardService.board.dto.letter.LetterDto;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.letter.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LetterApiController {

    private final LetterService letterService;

    @PostMapping("/user/letters/{id}")
    public ResponseEntity<?> save(@PathVariable long id, @RequestBody LetterDto.Request dto, @LoginUser UserDto.Response user) {
        return ResponseEntity.ok(letterService.sendLetter(id, user.getId(), dto));
    }

    @PostMapping("/posts/letters/{id}")
    public ResponseEntity<?> postsLetters(@PathVariable long id, @RequestBody LetterDto.Request dto, @LoginUser UserDto.Response user){
        return ResponseEntity.ok(letterService.sendLetter(id, user.getId(), dto));
    }
}
