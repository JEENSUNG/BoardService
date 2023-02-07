package boardService.board.service.post;

import boardService.board.domain.post.Likes;
import boardService.board.domain.post.Posts;
import boardService.board.domain.Role;
import boardService.board.domain.User;
import boardService.board.dto.post.PostsDto;
import boardService.board.dto.UserDto;
import boardService.board.repository.post.LikesRepository;
import boardService.board.repository.post.PostsRepository;
import boardService.board.repository.UserRepository;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final LikesRepository likeRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public long save(PostsDto.Request dto, String nickname){
        User user = userRepository.findByNickname(nickname);
        user.setPoint(user.getPoint() + 100);
        if(user.getPoint() >= 200){
            if(user.getRole().equals(Role.USER)) {
                user.setRole(Role.USER_VIP);
            }else if(user.getRole().equals(Role.SOCIAL)){
                user.setRole(Role.SOCIAL_VIP);
            }
            //승급하고 바로 비밀게시판 접근 가능
            Collection<GrantedAuthority> collection = new ArrayList<>();
            collection.add(() -> "ROLE_" + user.getRole());
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), collection);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        dto.setUser(user);
        Posts posts = dto.toEntity();
        postsRepository.save(posts);
        return posts.getId();
    }

    @Transactional(readOnly = true)
    public PostsDto.Response findById(long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        return new PostsDto.Response(posts);
    }

    @Transactional
    public void update(long id, PostsDto.Request dto){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        posts.update(dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void delete(long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        User user = posts.getUser();
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
        postsRepository.delete(posts);
    }

    @Transactional
    public void updateView(long id){
        postsRepository.updateView(id);
    }

    @Transactional(readOnly = true)
    public Page<Posts> pageList(Pageable pageable){
        return postsRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Posts> search(String keyword, Pageable pageable){
        return postsRepository.findByTitleContaining(keyword, pageable);
    }

    @Transactional
    public void like(long id, UserDto.Response dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("찾을 수 없는 게시글입니다."));
        Optional<Likes> likes = likeRepository.findByUserAndPosts(user, posts);
        if(likes.isEmpty()) {
            Likes entity = Likes.builder()
                    .user(user)
                    .posts(posts)
                    .likes(true)
                    .build();
            likeRepository.save(entity);
            postsRepository.updateLikeCount(id);
        }
        else{
            likeRepository.delete(likeRepository.findByUser(user));
            if(likes.get().isLikes()){
                postsRepository.updateLikeCountOf(id);
            }
        }
    }
    @Transactional
    public void disLike(long id, UserDto.Response dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("찾을 수 없는 게시글입니다."));
        Optional<Likes> likes = likeRepository.findByUserAndPosts(user, posts);
        if(likes.isEmpty()) {
            Likes entity = Likes.builder()
                    .user(user)
                    .posts(posts)
                    .disLikes(true)
                    .build();
            likeRepository.save(entity);
            postsRepository.updateDisLikeCount(id);
        }
        else{
            likeRepository.delete(likeRepository.findByUser(user));
            if(likes.get().isDisLikes()){
                postsRepository.updateDisLikeCountOf(id);
            }
        }
    }

    public boolean check(long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        return user.getPoint() >= 200 && (user.getRole().equals(Role.USER_VIP)) || (user.getRole().equals(Role.SOCIAL_VIP));
    }

    public UserDto.Response session(String username) {
        return new UserDto.Response(userRepository.findByUsername(username).orElseThrow(()
            -> new UsernameNotFoundException("찾을 수 없는 사용자입니다.")));
    }
}
