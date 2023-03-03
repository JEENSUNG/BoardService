package boardService.board.service.bank;

import boardService.board.domain.bank.Bank;
import boardService.board.domain.bank.BankName;
import boardService.board.domain.bank.BankTransaction;
import boardService.board.domain.user.User;
import boardService.board.dto.bank.BankDto;
import boardService.board.dto.bank.BankTransactionDto;
import boardService.board.dto.user.UserDto;
import boardService.board.repository.bank.BankRepository;
import boardService.board.repository.bank.BankTransactionRepository;
import boardService.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.module.FindException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final BankTransactionRepository bankTransactionRepository;

    @PostConstruct
    public void setDBInit(){
        if(bankRepository.count() > 0) {
            return;
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        Bank bank = new Bank(1L, 1L, "부산은행", BankName.BNK, newUUID, Integer.MAX_VALUE - 10000000);
        bankRepository.save(bank);
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
        newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        Bank bank2 = new Bank(2L, 2L, "경남은행", BankName.KNB, newUUID, Integer.MAX_VALUE - 10000000);
        bankRepository.save(bank2);
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
        newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        Bank bank3 = new Bank(3L, 3L, "기업은행", BankName.IBK, newUUID, Integer.MAX_VALUE - 10000000);
        bankRepository.save(bank3);
    }

    @Transactional
    public void bnkJoin(Long userId, BankDto.Request dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        user.setIsBank(true);
        Bank bank = dto.toEntity();
        bank.setName(user.getNickname());
        bank.setBankName(BankName.BNK);
        bank.setUserId(userId);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        bank.setAccount(newUUID);
        bankRepository.save(bank);
    }

    @Transactional
    public void knbJoin(Long userId, BankDto.Request dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        user.setIsBank(true);
        Bank bank = dto.toEntity();
        bank.setName(user.getNickname());
        bank.setBankName(BankName.KNB);
        bank.setUserId(userId);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        bank.setAccount(newUUID);
        bankRepository.save(bank);
    }

    @Transactional
    public void ibkJoin(Long userId, BankDto.Request dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        user.setIsBank(true);
        Bank bank = dto.toEntity();
        bank.setName(user.getNickname());
        bank.setBankName(BankName.IBK);
        bank.setUserId(userId);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newUUID = uuid.substring(0, 3) + "-" + uuid.substring(3, 8) + "-" + uuid.substring(8, 10);
        bank.setAccount(newUUID);
        bankRepository.save(bank);
    }

    @Transactional(readOnly = true)
    public boolean findUsername(String username) {
        Optional<Bank> bank = bankRepository.findByName(username);
        return bank.isPresent();
    }

    @Transactional(readOnly = true)
    public boolean findBank(Long id) {
        Optional<Bank> bank = bankRepository.findByUserId(id);
        return bank.isPresent();
    }

    @Transactional(readOnly = true)
    public BankDto.Response findUser(Long id) {
        Bank bank = bankRepository.findByUserId(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        return new BankDto.Response(bank);
    }

    @Transactional(readOnly = true)
    public UserDto.Response session(String username) {
        return new UserDto.Response(userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("찾을 수 없는 회원입니다.")));
    }

    @Transactional(readOnly = true)
    public Bank findBankOf(Long id) {
        return bankRepository.findByUserId(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
    }

    @Transactional
    public void startTransaction(Long id, BankName bankName) {
        Bank bank = bankRepository.findByUserId(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        User user = userRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        Bank adminBank = bankRepository.findTop1ByBankName(bankName);
        BankTransaction bankTransaction = BankTransaction.builder()
                .explanation("개설 축하금")
                .toUsername(bank.getName())
                .toAccount(bank.getAccount())
                .username(adminBank.getName())
                .toUsername(user.getNickname())
                .money(10000)
                .toBankName(bankName)
                .account(adminBank.getAccount())
                .bankName(adminBank.getBankName())
                .build();
        adminBank.setMoney(adminBank.getMoney() - 10000);
        bank.setMoney(bank.getMoney() + 10000);
        bankTransactionRepository.save(bankTransaction);
    }

    @Transactional(readOnly = true)
    public boolean check(BankDto.Check dto) {
        Optional<Bank> bank = bankRepository.findByName(dto.getName());
        return bank.isPresent() && (bank.get().getBankName().toString().equals(dto.getBankName()) && bank.get().getName().equals(dto.getName()) && bank.get().getAccount().equals(dto.getAccount()));
    }

    @Transactional
    public void transfer(long id, BankTransactionDto.Request dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        Bank bank = bankRepository.findByUserId(user.getId()).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        Bank bank2 = bankRepository.findByAccount(dto.getAccount()).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        BankTransaction bankTransaction = BankTransaction.builder()
                .bankName(bank.getBankName())
                .toBankName(BankName.valueOf(dto.getBankName()))
                .money(Integer.parseInt(dto.getMoney()))
                .account(bank.getAccount())
                .toAccount(dto.getAccount())
                .explanation(dto.getExplanation())
                .username(user.getNickname())
                .toUsername(dto.getUsername())
                .build();
        bankTransactionRepository.save(bankTransaction);
        bank.setMoney(bank.getMoney() - Integer.parseInt(dto.getMoney()));
        bank2.setMoney(bank2.getMoney() + Integer.parseInt(dto.getMoney()));
    }

    @Transactional(readOnly = true)
    public int getMoney(Long id) {
        Bank bank = bankRepository.findByUserId(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        return bank.getMoney();
    }

    @Transactional
    public void transferPlus(Long id, BankTransactionDto.Request dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        Bank bank = bankRepository.findByUserId(user.getId()).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        Bank bank2 = bankRepository.findByAccount(dto.getAccount()).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        BankTransaction bankTransaction = BankTransaction.builder()
                .bankName(bank.getBankName())
                .toBankName(BankName.valueOf(dto.getBankName()))
                .money(Integer.parseInt(dto.getMoney()))
                .account(bank.getAccount())
                .toAccount(dto.getAccount())
                .explanation(dto.getExplanation())
                .username(user.getNickname())
                .toUsername(dto.getUsername())
                .build();
        bankTransactionRepository.save(bankTransaction);
        bank.setMoney(bank.getMoney() - Integer.parseInt(dto.getMoney()) - 1500);
        bank2.setMoney(bank2.getMoney() + Integer.parseInt(dto.getMoney()));
        Bank adminBank = bankRepository.findTop1ByBankName(bank.getBankName());
        adminBank.setMoney(adminBank.getMoney() + 1500);
    }

    @Transactional(readOnly = true)
    public Page<BankTransaction> withdraw(long id, Pageable pageable) {
        Bank bank = bankRepository.findByUserId(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        return bankTransactionRepository.findAllByAccount(bank.getAccount(), pageable);
    }

    @Transactional(readOnly = true)
    public Page<BankTransaction> deposit(Long id, Pageable pageable) {
        Bank bank = bankRepository.findByUserId(id).orElseThrow(() -> new FindException("찾을 수 없는 회원입니다."));
        return bankTransactionRepository.findAllByToAccount(bank.getAccount(), pageable);
    }
}
