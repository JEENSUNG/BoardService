package boardService.board.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    USER_VIP("ROLE_VIP"),
    SOCIAL("ROLE_SOCIAL"),
    SOCIAL_VIP("ROLE_SOCIAL_VIP");

    private final String value;
}
