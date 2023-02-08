package boardService.board.service.secret;

import boardService.board.domain.user.Role;
import boardService.board.domain.user.User;
import boardService.board.domain.secret.SecretComment;
import boardService.board.domain.secret.SecretPosts;
import boardService.board.dto.UserDto;
import boardService.board.dto.secret.SecretCommentDto;
import boardService.board.repository.user.UserRepository;
import boardService.board.repository.secret.SecretCommentRepository;
import boardService.board.repository.secret.SecretPostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SecretCommentService {

    private final SecretCommentRepository secretCommentRepository;
    private final UserRepository userRepository;
    private final SecretPostsRepository secretPostsRepository;

    @Transactional
    public long save(long id, String nickname, SecretCommentDto.Request dto){
        User user = userRepository.findByNickname(nickname);
        SecretPosts secretPosts = secretPostsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다." + id));
        user.setPoint(user.getPoint() + 50);
        if(user.getPoint() >= 200){
            if(user.getRole().equals(Role.USER)) {
                user.setRole(Role.USER_VIP);
            }else if(user.getRole().equals(Role.SOCIAL)){
                user.setRole(Role.SOCIAL_VIP);
            }
            Collection<GrantedAuthority> collection = new ArrayList<>();
            collection.add(() -> "ROLE_" + user.getRole());
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), collection);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        dto.setUser(user);
        dto.setPosts(secretPosts);
        SecretComment secretComment = dto.toEntity();
        secretCommentRepository.save(secretComment);
        return secretComment.getId();
    }

    @Transactional(readOnly = true)
    public List<SecretCommentDto.Response> findAll(long id){
        SecretPosts secretPosts = secretPostsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        List<SecretComment> secretComments = secretPosts.getComments();
        return secretComments.stream().map(SecretCommentDto.Response::new).collect(Collectors.toList());
    }

    @Transactional
    public void update(long id, SecretCommentDto.Request dto){
        SecretComment secretComment = secretCommentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));
        secretComment.update(dto.getComment());
    }

    @Transactional
    public void delete(long id){
        SecretComment secretComment = secretCommentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));
        User user = secretComment.getUser();
        user.setPoint(user.getPoint() - 50);
        if(user.getPoint() < 200){
            if(user.getRole().equals(Role.USER_VIP)) {
                user.setRole(Role.USER);
            }else if(user.getRole().equals(Role.SOCIAL_VIP)){
                user.setRole(Role.SOCIAL);
            }
            Collection<GrantedAuthority> collection = new ArrayList<>();
            collection.add(() -> "ROLE_" + user.getRole());
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), collection);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        secretCommentRepository.delete(secretComment);
    }


    public boolean check(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        return user.getPoint() >= 200 && (user.getRole().equals(Role.USER_VIP)) || (user.getRole().equals(Role.SOCIAL_VIP));
    }

    public UserDto.Response session(String username) {
        return new UserDto.Response(userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("찾을 수 없는 사용자입니다.")));
    }
}
