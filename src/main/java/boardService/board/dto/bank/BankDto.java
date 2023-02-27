package boardService.board.dto.bank;

import boardService.board.domain.bank.Bank;
import boardService.board.domain.bank.BankName;
import lombok.*;

import java.io.Serializable;

public class BankDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private String name;
        /* Dto -> Entity */
        public Bank toEntity() {
            return Bank.builder()
                    .name(name)
                    .build();
        }
    }
    @Getter
    public static class Response implements Serializable {
        private final String name;
        private final BankName bankName;
        private final String createdDate;
        private final int money;
        private final String account;

        public Response(Bank bank) {
            this.name = bank.getName();
            this.bankName = bank.getBankName();
            this.createdDate = bank.getCreatedDate();
            this.money = bank.getMoney();
            this.account = bank.getAccount();
        }
    }
}
