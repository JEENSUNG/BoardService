package boardService.board.domain.bank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankName {
    BNK("BANK_BNK"),
    KNB("BANK_KNB"),
    IBK("BANK_IBK");

    private final String value;
}
