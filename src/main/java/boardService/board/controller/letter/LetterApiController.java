package boardService.board.controller.letter;

import boardService.board.dto.letter.LetterDto;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.letter.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LetterApiController {

    private final LetterService letterService;

    @PostMapping("/user/letters/{id}")
    public ResponseEntity<?> save(@PathVariable long pageNum, @PathVariable long id,
                                  @RequestBody @Valid LetterDto.Request dto, @LoginUser UserDto.Response user) {
        return ResponseEntity.ok(letterService.sendLetter(id, user.getId(), dto, pageNum));
    }

    @PostMapping("/posts/{pageNum}/letters/{id}")
    public ResponseEntity<?> postsLetters(@PathVariable long pageNum, @PathVariable long id,
                                          @RequestBody @Valid LetterDto.Request dto, @LoginUser UserDto.Response user){
        return ResponseEntity.ok(letterService.sendLetter(id, user.getId(), dto, pageNum));
    }

    @DeleteMapping("/sendLetters/delete/{id}")
    public ResponseEntity<?> deleteSendLetters(@PathVariable long id){
        letterService.deleteToUserLetter(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/takenLetters/delete/{id}")
    public ResponseEntity<?> deleteTakenLetters(@PathVariable long id){
        letterService.deleteFromUserLetter(id);
        return ResponseEntity.noContent().build();
    }
}
