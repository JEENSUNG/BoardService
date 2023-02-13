package boardService.board.service.secret;

import boardService.board.domain.user.Role;
import boardService.board.domain.user.User;
import boardService.board.domain.secret.SecretLikes;
import boardService.board.domain.secret.SecretPosts;
import boardService.board.dto.user.UserDto;
import boardService.board.dto.secret.SecretPostsDto;
import boardService.board.repository.user.UserRepository;
import boardService.board.repository.secret.SecretLikesRepository;
import boardService.board.repository.secret.SecretPostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecretPostsService {

    private final SecretPostsRepository secretPostsRepository;
    private final UserRepository userRepository;
    private final SecretLikesRepository secretLikesRepository;

    @Transactional
    public long save(SecretPostsDto.Request dto, String nickname){
        User user = userRepository.findByNickname(nickname);
        user.setPoint(user.getPoint() + 100);
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
        SecretPosts secretPosts = dto.toEntity();
        secretPostsRepository.save(secretPosts);
        return secretPosts.getId();
    }

    @Transactional(readOnly = true)
    public SecretPostsDto.Response findById(long id){
        SecretPosts secretPosts = secretPostsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        return new SecretPostsDto.Response(secretPosts);
    }

    @Transactional
    public void update(long id, SecretPostsDto.Request dto){
        SecretPosts secretPosts = secretPostsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        secretPosts.update(dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void delete(long id){
        SecretPosts secretPosts = secretPostsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        User user = secretPosts.getUser();
        user.setPoint(user.getPoint() - 100);
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
        secretPostsRepository.delete(secretPosts);
    }

    @Transactional
    public void updateView(long id){
        secretPostsRepository.updateView(id);
    }

    @Transactional(readOnly = true)
    public Page<SecretPosts> pageList(Pageable pageable){
        return secretPostsRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<SecretPosts> search(String keyword, Pageable pageable){
        return secretPostsRepository.findByTitleContaining(keyword, pageable);
    }

    @Transactional
    public void like(long id, UserDto.Response dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        SecretPosts secretPosts = secretPostsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("찾을 수 없는 게시글입니다."));
        Optional<SecretLikes> likes = secretLikesRepository.findByUserAndPosts(user, secretPosts);
        if(likes.isEmpty()) {
            SecretLikes entity = SecretLikes.builder()
                    .user(user)
                    .posts(secretPosts)
                    .likes(true)
                    .build();
            secretLikesRepository.save(entity);
            secretPostsRepository.updateLikeCount(id);
        }
        else{
            secretLikesRepository.delete(secretLikesRepository.findByUser(user));
            if(likes.get().isLikes()){
                secretPostsRepository.updateLikeCountOf(id);
            }
        }
    }
    @Transactional
    public void disLike(long id, UserDto.Response dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        SecretPosts secretPosts = secretPostsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("찾을 수 없는 게시글입니다."));
        Optional<SecretLikes> likes = secretLikesRepository.findByUserAndPosts(user, secretPosts);
        if(likes.isEmpty()) {
            SecretLikes entity = SecretLikes.builder()
                    .user(user)
                    .posts(secretPosts)
                    .disLikes(true)
                    .build();
            secretLikesRepository.save(entity);
            secretPostsRepository.updateDisLikeCount(id);
        }
        else{
            secretLikesRepository.delete(secretLikesRepository.findByUser(user));
            if(likes.get().isDisLikes()){
                secretPostsRepository.updateDisLikeCountOf(id);
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean check(long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        return user.getPoint() >= 200 && (user.getRole().equals(Role.USER_VIP)) || (user.getRole().equals(Role.SOCIAL_VIP));
    }

    @Transactional(readOnly = true)
    public UserDto.Response session(String username) {
        return new UserDto.Response(userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("찾을 수 없는 사용자입니다.")));
    }
}
