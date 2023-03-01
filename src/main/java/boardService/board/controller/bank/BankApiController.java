package boardService.board.controller.bank;

import boardService.board.domain.bank.Bank;
import boardService.board.domain.bank.BankName;
import boardService.board.dto.util.ErrorMessage;
import boardService.board.dto.util.Result;
import boardService.board.dto.bank.BankDto;
import boardService.board.dto.bank.BankTransactionDto;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.bank.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank")
public class BankApiController {

    private final BankService bankService;

    @PostMapping("/check/{username}")
    public ResponseEntity<?> usernameCheck(@LoginUser UserDto.Response user,
                                           @PathVariable String username){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        return ResponseEntity.ok(new Result<>(bankService.findUsername(username)));
    }

    @PostMapping("/save/bnk")
    public ResponseEntity<?> bnkJoin(@LoginUser UserDto.Response user, @RequestBody @Valid BankDto.Request dto,
                                     HttpSession httpSession){
        if(user == null || bankService.findBank(user.getId())){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        bankService.bnkJoin(user.getId(), dto);
        bankService.startTransaction(user.getId(), BankName.BNK);
        UserDto.Response entity = bankService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/save/knb")
    public ResponseEntity<?> knbJoin(@LoginUser UserDto.Response user, @RequestBody @Valid BankDto.Request dto,
                                     HttpSession httpSession){
        if(user == null || bankService.findBank(user.getId())){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        bankService.knbJoin(user.getId(), dto);
        bankService.startTransaction(user.getId(), BankName.KNB);
        UserDto.Response entity = bankService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/save/ibk")
    public ResponseEntity<?> ibkJoin(@LoginUser UserDto.Response user, @RequestBody @Valid BankDto.Request dto,
                                     HttpSession httpSession){
        if(user == null || bankService.findBank(user.getId())){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        bankService.ibkJoin(user.getId(), dto);
        bankService.startTransaction(user.getId(), BankName.IBK);
        UserDto.Response entity = bankService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/find/{id}")
    public ResponseEntity<?> findBank(@LoginUser UserDto.Response user, @PathVariable long id){
        if(user == null || !bankService.findBank(id)){
            throw new IllegalArgumentException("접근할 수 없습니다");
        }
        Bank bank = bankService.findBankOf(id);
        return ResponseEntity.ok(new Result<>(bank.getAccount()));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@LoginUser UserDto.Response user, @RequestBody @Valid BankTransactionDto.Request dto,
                                      HttpSession httpSession){
        if(user == null || !bankService.findBank(user.getId())){
            return new ResponseEntity<>(new ErrorMessage<>( "접근할 수 없습니다."), HttpStatus.FORBIDDEN);
        }
        BankDto.Check check = new BankDto.Check(dto.getBankName(), dto.getUsername(), dto.getAccount());
        if(!bankService.check(check)){
            return new ResponseEntity<>(new ErrorMessage<>("유효하지 않은 계좌입니다."), HttpStatus.BAD_REQUEST);
        }
        if(dto.getMoney() > bankService.getMoney(user.getId())){
            return new ResponseEntity<>(new ErrorMessage<>("잔액이 부족합니다."), HttpStatus.CONFLICT);
        }
        bankService.transfer(user.getId(), dto);
        UserDto.Response entity = bankService.session(user.getUsername());
        httpSession.setAttribute("user", entity);
        return ResponseEntity.ok(new Result<>("success"));
    }

    @PostMapping("/check")
    public ResponseEntity<?> bankCheck(@LoginUser UserDto.Response user, @RequestBody @Valid BankDto.Check dto){
        if(user == null || !bankService.findBank(user.getId())){
            return new ResponseEntity<>(new ErrorMessage<>("접근할 수 없습니다."), HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(new Result<>(bankService.check(dto)));
    }
}
