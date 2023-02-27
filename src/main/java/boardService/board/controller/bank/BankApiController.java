package boardService.board.controller.bank;

import boardService.board.domain.bank.BankName;
import boardService.board.dto.Result;
import boardService.board.dto.bank.BankDto;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.bank.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank")
public class BankApiController {

    private final BankService bankService;

    @PostMapping("/check/{username}")
    public ResponseEntity<?> usernameCheck(@LoginUser UserDto.Response user,
                                           @PathVariable String username, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("user", user);
        return ResponseEntity.ok(new Result<>(bankService.findUsername(username)));
    }

    @PostMapping("/save/bnk")
    public ResponseEntity<?> bnkJoin(@LoginUser UserDto.Response user, @RequestBody BankDto.Request dto,
                                     Model model, HttpSession httpSession){
        if(user == null || bankService.findBank(user.getId())){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("user", user);
        bankService.bnkJoin(user.getId(), dto);
        UserDto.Response entity = bankService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/save/knb")
    public ResponseEntity<?> knbJoin(@LoginUser UserDto.Response user, @RequestBody BankDto.Request dto,
                                     Model model, HttpSession httpSession){
        if(user == null || bankService.findBank(user.getId())){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("user", user);
        bankService.knbJoin(user.getId(), dto);
        UserDto.Response entity = bankService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/save/ibk")
    public ResponseEntity<?> ibkJoin(@LoginUser UserDto.Response user, @RequestBody BankDto.Request dto,
                                     Model model, HttpSession httpSession){
        if(user == null || bankService.findBank(user.getId())){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("user", user);
        bankService.ibkJoin(user.getId(), dto);
        UserDto.Response entity = bankService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok("success");
    }
}
