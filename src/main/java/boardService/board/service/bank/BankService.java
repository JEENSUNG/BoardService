package boardService.board.service.bank;

import boardService.board.domain.bank.Bank;
import boardService.board.domain.bank.BankName;
import boardService.board.domain.user.User;
import boardService.board.dto.bank.BankDto;
import boardService.board.dto.user.UserDto;
import boardService.board.repository.bank.BankRepository;
import boardService.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    @Transactional
    public void bnkJoin(Long userId, BankDto.Request dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        user.setIsBank(true);
        Bank bank = dto.toEntity();
        bank.setBankName(BankName.BNK);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        bank.setAccount(newUUID);
        bank.setMoney(10000);
        bankRepository.save(bank);
    }

    @Transactional
    public void knbJoin(Long userId, BankDto.Request dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        user.setIsBank(true);
        Bank bank = dto.toEntity();
        bank.setBankName(BankName.KNB);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        bank.setAccount(newUUID);
        bank.setMoney(10000);
        bankRepository.save(bank);
    }

    @Transactional
    public void ibkJoin(Long userId, BankDto.Request dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        user.setIsBank(true);
        Bank bank = dto.toEntity();
        bank.setBankName(BankName.IBK);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        bank.setAccount(newUUID);
        bank.setMoney(10000);
        bankRepository.save(bank);
    }

    @Transactional(readOnly = true)
    public boolean findUsername(String username) {
        Optional<Bank> bank = bankRepository.findByName(username);
        return bank.isPresent();
    }

    @Transactional(readOnly = true)
    public boolean findBank(Long id) {
        Optional<Bank> bank = bankRepository.findById(id);
        return bank.isPresent();
    }

    @Transactional(readOnly = true)
    public BankDto.Response findUser(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        return new BankDto.Response(bank);
    }

    @Transactional(readOnly = true)
    public UserDto.Response session(String username) {
        return new UserDto.Response(userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("찾을 수 없는 사용자입니다.")));
    }
}
