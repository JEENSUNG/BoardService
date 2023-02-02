package boardService.board.service;

import boardService.board.domain.Comment;
import boardService.board.domain.Posts;
import boardService.board.domain.Role;
import boardService.board.domain.User;
import boardService.board.dto.CommentDto;
import boardService.board.repository.CommentRepository;
import boardService.board.repository.PostsRepository;
import boardService.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public long save(long id, String nickname, CommentDto.Request dto){
        User user = userRepository.findByNickname(nickname);
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다." + id));
        user.setPoint(user.getPoint() + 50);
        if(user.getPoint() >= 200){
            if(user.getRole().equals(Role.USER)) {
                user.setRole(Role.USER_VIP);
            }else if(user.getRole().equals(Role.SOCIAL)){
                user.setRole(Role.SOCIAL_VIP);
            }
        }
        dto.setUser(user);
        dto.setPosts(posts);
        Comment comment = dto.toEntity();
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentDto.Response> findAll(long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        List<Comment> comments = posts.getComments();
        return comments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }

    @Transactional
    public void update(long id, CommentDto.Request dto){
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));
        comment.update(dto.getComment());
    }

    @Transactional
    public void delete(long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));
        User user = comment.getUser();
        user.setPoint(user.getPoint() - 50);
        if(user.getPoint() < 200){
            if(user.getRole().equals(Role.USER_VIP)) {
                user.setRole(Role.USER);
            }else if(user.getRole().equals(Role.SOCIAL_VIP)){
                user.setRole(Role.SOCIAL);
            }
        }
        commentRepository.delete(comment);
    }


    public boolean check(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        return user.getPoint() >= 200 && (user.getRole().equals(Role.USER_VIP)) || (user.getRole().equals(Role.SOCIAL_VIP));
    }
}
