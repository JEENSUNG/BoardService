package boardService.board.dto.post;
import boardService.board.domain.post.Comment;
import boardService.board.domain.post.Posts;
import boardService.board.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * request, response DTO 클래스를 하나로 묶어 InnerStaticClass로 한 번에 관리
 */
public class CommentDto {

    /** 댓글 Service 요청을 위한 DTO 클래스 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        @NotBlank(message = "내용을 입력해주세요.")
        private String comment;
        private User user;
        private Posts posts;
        /* Dto -> Entity */
        public Comment toEntity() {

            return Comment.builder()
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
    public static class Response  implements Serializable {
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
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.nickname = comment.getUser().getNickname();
            this.userId = comment.getUser().getId();
            this.postsId = comment.getPosts().getId();
        }
    }
}