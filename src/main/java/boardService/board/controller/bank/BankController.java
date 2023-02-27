package boardService.board.controller.bank;

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
        model.addAttribute("user", user);
        if(!user.isBank()){
            return "bank/bank-join";
        }
        return "bank/bank-service";
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
            throw new IllegalArgumentException("찾을 수 없는 회원입니다.");
        }
        BankDto.Response bank = bankService.findUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("bank", bank);
        return "bank/bank-welcome";
    }

    @GetMapping("/bank/welcome/knb")
    public String welcomeKnb(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("찾을 수 없는 회원입니다.");
        }
        BankDto.Response bank = bankService.findUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("bank", bank);
        return "bank/bank-welcome";
    }

    @GetMapping("/bank/welcome/ibk")
    public String welcomeIbk(@LoginUser UserDto.Response user, Model model){
        if(user == null){
            throw new IllegalArgumentException("찾을 수 없는 회원입니다.");
        }
        BankDto.Response bank = bankService.findUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("bank", bank);
        return "bank/bank-welcome";
    }
}
