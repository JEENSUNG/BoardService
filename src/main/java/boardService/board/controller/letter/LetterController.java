package boardService.board.controller.letter;

import boardService.board.domain.letter.Letter;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.letter.LetterService;
import boardService.board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class LetterController {

    private final UserService userService;
    private final LetterService letterService;

    @GetMapping("/sendLetters")
    public String sendLetters(@LoginUser UserDto.Response user, Model model,
                              @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        if(user != null){
            Page<Letter> letters = letterService.findSendList(user.getId(), pageable);
            model.addAttribute("user", user);
            model.addAttribute("letters", letters);
            model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
            model.addAttribute("next", pageable.next().getPageNumber());
            model.addAttribute("hasNext", letters.hasNext());
            model.addAttribute("hasPrev", letters.hasPrevious());
        }
        return "/user/sendLetters";
    }

    @GetMapping("/takenLetters")
    public String takenLetters(@LoginUser UserDto.Response user, Model model,
                               @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        if(user != null){
            Page<Letter> letters = letterService.findTakenList(user.getId(), pageable);
            model.addAttribute("user", user);
            model.addAttribute("letters", letters);
            model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
            model.addAttribute("next", pageable.next().getPageNumber());
            model.addAttribute("hasNext", letters.hasNext());
            model.addAttribute("hasPrev", letters.hasPrevious());
        }
        return "/user/takenLetters";
    }

    @GetMapping("/user/letters/{id}")
    public String userLetter(@PathVariable long id, Model model, @LoginUser UserDto.Response user){
        if(user != null) {
            String nickname = userService.findByNickname(id);
            model.addAttribute("nickname", nickname);
            model.addAttribute("to", id);
            model.addAttribute("user", user);
        }
        return "user/user-letter";
    }

    @GetMapping("/posts/{pageNum}/letters/{id}")
    public String postsLetters(@PathVariable long pageNum, @PathVariable long id, Model model, @LoginUser UserDto.Response user) {
        if(user != null) {
            String nickname = userService.findByNickname(id);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("nickname", nickname);
            model.addAttribute("to", id);
            model.addAttribute("user", user);
        }
        return "posts/user-letter";
    }

    @GetMapping("/letters/{id}/send")
    public String sendLetterRead(@PathVariable long id, Model model, @LoginUser UserDto.Response user){
        if(user != null){
            Letter letter = letterService.findLetter(id);
            //제 3자는 열람 x
            if(user.getId() != letter.getToUser() && user.getId() != letter.getFromUser()){
                throw new IllegalArgumentException("접근할 수 없습니다.");
            }
            model.addAttribute("user", user);
            model.addAttribute("letter", letter);
        }
        return "user/letter-read-send";
    }

    @GetMapping("/letters/{id}/taken")
    public String takenLetterRead(@PathVariable long id, Model model, @LoginUser UserDto.Response user){
        if(user != null){
            Letter letter = letterService.findLetter(id);
            //제 3자는 열람 x
            if(user.getId() != letter.getToUser() && user.getId() != letter.getFromUser()){
                throw new IllegalArgumentException("접근할 수 없습니다.");
            }
            model.addAttribute("user", user);
            model.addAttribute("letter", letter);
        }
        return "user/letter-read-taken";
    }
}
