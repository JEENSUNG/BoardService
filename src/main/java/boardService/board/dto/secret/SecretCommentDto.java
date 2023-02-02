package boardService.board.dto.secret;

import boardService.board.domain.User;
import boardService.board.domain.secret.SecretComment;
import boardService.board.domain.secret.SecretPosts;
import lombok.*;

public class SecretCommentDto {

    /** 댓글 Service 요청을 위한 DTO 클래스 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String comment;
//        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
//        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String createdDate;
        private String modifiedDate;
        private User user;
        private SecretPosts posts;
        /* Dto -> Entity */
        public SecretComment toEntity() {

            return SecretComment.builder()
                    .id(id)
                    .comment(comment)
//                    .createdDate(createdDate)
//                    .modifiedDate(modifiedDate)
                    .user(user)
                    .posts(posts)
                    .build();
        }
    }

    /**
     * 댓글 정보를 리턴할 응답(Response) 클래스
     * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
     * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
     */
//    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private final Long id;
        private final String comment;
//        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
//        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private final String createdDate;
        private final String modifiedDate;
        private final String nickname;
        private final Long userId;
        private final Long postsId;
        /* Entity -> Dto*/
        public Response(SecretComment secretComment) {
            this.id = secretComment.getId();
            this.comment = secretComment.getComment();
            this.createdDate = secretComment.getCreatedDate();
            this.modifiedDate = secretComment.getModifiedDate();
            this.nickname = secretComment.getUser().getNickname();
            this.userId = secretComment.getUser().getId();
            this.postsId = secretComment.getPosts().getId();
        }
    }
}