package boardService.board.repository.bank;

import boardService.board.domain.bank.BankTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    Page<BankTransaction> findAllByAccount(String account, Pageable pageable);
    Page<BankTransaction> findAllByToAccount(String toAccount, Pageable pageable);

}
