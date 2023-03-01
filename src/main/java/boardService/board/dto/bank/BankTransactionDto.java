package boardService.board.dto.bank;

import boardService.board.domain.bank.BankName;
import boardService.board.domain.bank.BankTransaction;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class BankTransactionDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "BNK, IBK, KNB 중에서 입력해주세요.")
        private String bankName;

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        private String username;
        @NotBlank(message = "xxx-xxxxx-xx 형태로 입력해주세요.")
        private String account;
        @NotBlank(message = "어떤 용도로 송금하는지 입력해주세요.")
        private String explanation;

        @NotBlank(message = "현재 보유 금액 이하의 금액을 입력해주세요.")
        private int money;

        /* Dto -> Entity */
        public BankTransaction toEntity() {
            return BankTransaction.builder()
                    .bankName(BankName.valueOf(bankName))
                    .username(username)
                    .account(account)
                    .explanation(explanation)
                    .build();
        }
    }
    @Getter
    public static class Response implements Serializable {
        private final BankName bankName;

        private final BankName toBankName;

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
