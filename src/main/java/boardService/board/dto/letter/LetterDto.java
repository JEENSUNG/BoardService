package boardService.board.dto.letter;

import boardService.board.domain.letter.Letter;
import lombok.*;

public class LetterDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private Long id;
        private String title;
        private String sendUsername;
        private String takenUsername;
        private String content;
        private long toUser;
        private long fromUser;

        /* Dto -> Entity */
        public Letter toEntity() {
            return Letter.builder()
                    .id(id)
                    .title(title)
                    .sendUsername(sendUsername)
                    .takenUsername(takenUsername)
                    .content(content)
                    .toUser(toUser)
                    .fromUser(fromUser)
                    .build();
        }
    }

    @Getter
    public static class Response{
        private final Long id;
        private final String title;
        private final String sendUsername;
        private final String takenUsername;
        private final String content;
        private final long toUser;
        private final long fromUser;
        private final String createdDate;

        public Response(Letter letter) {
            this.id = letter.getId();
            this.title = letter.getTitle();
            this.sendUsername = letter.getSendUsername();
            this.takenUsername = letter.getTakenUsername();
            this.content = letter.getContent();
            this.createdDate = letter.getCreatedDate();
            this.toUser = letter.getToUser();
            this.fromUser = letter.getFromUser();
        }
    }
}
