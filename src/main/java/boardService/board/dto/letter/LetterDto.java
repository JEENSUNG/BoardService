package boardService.board.dto.letter;

import boardService.board.domain.letter.Letter;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LetterDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        private String sendUsername;
        private String takenUsername;
        @NotBlank(message = "내용을 입력해주세요.")
        private String content;
        private long toUser;
        private long fromUser;
        private long pageNum;

        /* Dto -> Entity */
        public Letter toEntity() {
            return Letter.builder()
                    .title(title)
                    .sendUsername(sendUsername)
                    .takenUsername(takenUsername)
                    .content(content)
                    .toUser(toUser)
                    .fromUser(fromUser)
                    .pageNum(pageNum)
                    .build();
        }
    }
    @Getter
    public static class Response implements Serializable {
        private final String title;
        private final String sendUsername;
        private final String takenUsername;
        private final String content;
        private final String createdDate;
        private final long toUser;
        private final long fromUser;
        private final long pageNum;
        public Response(Letter letter) {
            this.title = letter.getTitle();
            this.sendUsername = letter.getSendUsername();
            this.takenUsername = letter.getTakenUsername();
            this.content = letter.getContent();
            this.createdDate = letter.getCreatedDate();
            this.toUser = letter.getToUser();
            this.fromUser = letter.getFromUser();
            this.pageNum = letter.getPageNum();
        }
    }
}
