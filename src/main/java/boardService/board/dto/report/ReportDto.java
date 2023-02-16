package boardService.board.dto.report;

import boardService.board.domain.report.Report;
import boardService.board.domain.user.User;
import lombok.*;

public class ReportDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private String title;
        private User user;
        private String content;
        private long toUser;
        private String nickname;
        private String username;
        private boolean isRemoved;

        /* Dto -> Entity */
        public Report toEntity() {
            return Report.builder()
                    .title(title)
                    .user(user)
                    .content(content)
                    .toUser(toUser)
                    .nickname(nickname)
                    .username(username)
                    .isRemoved(true)
                    .build();
        }
    }
    @Getter
    public static class Response{
        private final String title;
        private final long userId;
        private final String content;
        private final String createdDate;
        private final long toUser;
        private final String nickname;
        private final String username;
        private final boolean isRemoved;

        public Response(Report report) {
            this.title = report.getTitle();
            this.content = report.getContent();
            this.createdDate = report.getCreatedDate();
            this.toUser = report.getToUser();
            this.userId = report.getUser().getId();
            this.nickname = report.getNickname();
            this.username = report.getUsername();
            this.isRemoved = report.isRemoved();
        }
    }
}
