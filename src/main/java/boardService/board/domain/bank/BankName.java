package boardService.board.domain.bank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankName {
    BNK("부산은행"),
    KNB("경남은행"),
    IBK("기업은행");

    private final String value;
}
