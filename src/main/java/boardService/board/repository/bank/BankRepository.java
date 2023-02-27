package boardService.board.repository.bank;

import boardService.board.domain.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByName(String username);
}
