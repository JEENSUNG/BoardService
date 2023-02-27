package boardService.board.dto.bank;

import boardService.board.domain.bank.BankTransaction;
import lombok.*;

import java.io.Serializable;

public class BankTransactionDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private String bankName;

        private String toBankName;

        private String username;

        private String toUsername;

        private String account;

        private String toAccount;

        private String explanation;

        private int money;

        /* Dto -> Entity */
        public BankTransaction toEntity() {
            return BankTransaction.builder()
                    .bankName(bankName)
                    .toBankName(toBankName)
                    .username(username)
                    .toUsername(toUsername)
                    .account(account)
                    .toAccount(toAccount)
                    .explanation(explanation)
                    .money(money)
                    .build();
        }
    }
    @Getter
    public static class Response implements Serializable {
        private final String bankName;

        private final String toBankName;

        private final String username;

        private final String toUsername;

        private final String account;

        private final String toAccount;

        private final String explanation;

        private final String createdDate;

        private final int money;

        public Response(BankTransaction bankTransaction) {
            this.bankName = bankTransaction.getBankName();
            this.toBankName = bankTransaction.getToBankName();
            this.username = bankTransaction.getUsername();
            this.toUsername = bankTransaction.getToUsername();
            this.account = bankTransaction.getAccount();
            this.toAccount = bankTransaction.getToAccount();
            this.explanation = bankTransaction.getExplanation();
            this.money = bankTransaction.getMoney();
            this.createdDate = bankTransaction.getCreatedDate();
        }
    }
}
