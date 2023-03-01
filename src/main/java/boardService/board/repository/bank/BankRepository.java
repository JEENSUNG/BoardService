package boardService.board.repository.bank;

import boardService.board.domain.bank.Bank;
import boardService.board.domain.bank.BankName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByName(String username);

    Bank findTop1ByBankName(BankName bankName);

    Optional<Bank> findByUserId(Long id);

    Optional<Bank> findByAccount(String account);
}
