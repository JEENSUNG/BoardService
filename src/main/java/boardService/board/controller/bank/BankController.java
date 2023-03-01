package boardService.board.controller.bank;

import boardService.board.domain.bank.Bank;
import boardService.board.domain.user.User;
import boardService.board.dto.bank.BankDto;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.bank.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("/bank")
    public String bankIndex(@LoginUser UserDto.Response user, Model model){
        if(user != null){
            model.addAttribute("user", user);
            if(!user.isBank()){
                return "bank/bank-join";
            }
            Bank bank = bankService.findBankOf(user.getId());
            model.addAttribute("bank", bank);
            return "bank/bank-service";
        }
        return "user/user-login";
    }

    @GetMapping("/bank/join/bnk")
    public String bnkBank(@LoginUser UserDto.Response user, Model model){
        if(user.isBank()){
            throw new IllegalArgumentException("이미 계좌를 보유하고 있습니다.");
        }
        model.addAttribute("user", user);
        return "bank/bnk-join";
    }

    @GetMapping("/bank/join/knb")
    public String knbBank(@LoginUser UserDto.Response user, Model model){
        if(user.isBank()){
            throw new IllegalArgumentException("이미 계좌를 보유하고 있습니다.");
        }
        model.addAttribute("user", user);
        return "bank/knb-join";
    }

    @GetMapping("/bank/join/ibk")
    public String ibkBank(@LoginUser UserDto.Response user, Model model){
        if(user.isBank()){
            throw new IllegalArgumentException("이미 계좌를 보유하고 있습니다.");
        }
        model.addAttribute("user", user);
       return "bank/ibk-join";
    }

    @GetMapping("/bank/welcome/bnk")
    public String welcomeBnk(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        BankDto.Response bank = bankService.findUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("bank", bank);
        return "bank/bank-welcome";
    }

    @GetMapping("/bank/welcome/knb")
    public String welcomeKnb(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        BankDto.Response bank = bankService.findUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("bank", bank);
        return "bank/bank-welcome";
    }

    @GetMapping("/bank/welcome/ibk")
    public String welcomeIbk(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        BankDto.Response bank = bankService.findUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("bank", bank);
        return "bank/bank-welcome";
    }

    @GetMapping("/bank/transfer")
    public String transfer(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        Bank bank = bankService.findBankOf(user.getId());
        model.addAttribute("bank", bank);
        model.addAttribute("user", user);
        return "bank/transfer";
    }

    @GetMapping("/bank/withdraw")
    public String withdraw(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("user", user);
        return "bank/withdraw";
    }

    @GetMapping("/bank/deposit")
    public String deposit(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("user", user);
        return "bank/deposit";
    }

    @GetMapping("/bank/transfer-after")
    public String transferAfter(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("user", user);
        Bank bank = bankService.findBankOf(user.getId());
        model.addAttribute("bank", bank);
        return "bank/transfer-after";
    }
}
