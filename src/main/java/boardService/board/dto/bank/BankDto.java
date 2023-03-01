package boardService.board.dto.bank;

import boardService.board.domain.bank.Bank;
import boardService.board.domain.bank.BankName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class BankDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Check{
        @NotBlank(message = "BNK, IBK, KNB 중에서 입력해주세요.")
        private String bankName;
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        private String name;
        @NotBlank(message = "xxx-xxxxx-xx 형태로 입력해주세요.")
        private String account;
    }

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
