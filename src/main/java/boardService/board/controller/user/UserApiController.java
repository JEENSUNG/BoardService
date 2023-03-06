package boardService.board.controller.user;

import boardService.board.dto.user.UserDto;
import boardService.board.dto.util.ErrorMessage;
import boardService.board.dto.util.Result;
import boardService.board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PutMapping
    public ResponseEntity<?> modify(@RequestBody @Valid UserDto.Request dto){
        userService.modify(dto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/find")
    public ResponseEntity<?> userFind(@RequestBody @Valid UserDto.Find dto){
        System.out.println(dto.getUsername());
        if(userService.userFind(dto)){
            return ResponseEntity.ok(new Result<>(userService.findUser(dto.getUsername())));
        }
        return new ResponseEntity<>(new ErrorMessage<>("찾을 수 없는 회원입니다. 다시 시도해주세요"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/password-modify/{id}")
    public ResponseEntity<?> passwordModify(@RequestBody @Valid UserDto.Password dto, @PathVariable long id){
        if(!dto.getPassword1().equals(dto.getPassword2())){
            return new ResponseEntity<>(new ErrorMessage<>("비밀번호가 서로 다릅니다."), HttpStatus.BAD_REQUEST);
        }
        userService.passwordModify(id, dto);
        return ResponseEntity.ok(new Result<>("success"));
    }
}
