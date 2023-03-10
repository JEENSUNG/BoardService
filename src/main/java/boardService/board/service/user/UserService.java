package boardService.board.service.user;

import boardService.board.domain.letter.Letter;
import boardService.board.domain.user.Role;
import boardService.board.domain.user.User;
import boardService.board.dto.letter.LetterDto;
import boardService.board.dto.user.UserDto;
import boardService.board.repository.letter.LetterRepository;
import boardService.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.annotation.PostConstruct;
import java.lang.module.FindException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    public void userDBInit(){
        if(userRepository.count() > 0){
            return;
        }
        String raw = "ADMINADMIN1!";
        String password = encoder.encode(raw);
        User user = new User(1L, "ADMIN", "ADMIN", password, "ADMIN@ADMIN.COM", Role.ADMIN, 10000000, true);
        userRepository.save(user);
        User user2 = new User(2L, "ADMIN2", "ADMIN2", password, "ADMIN2@ADMIN.COM", Role.ADMIN, 10000000, true);
        userRepository.save(user2);
        User user3 = new User(3L, "ADMIN3", "ADMIN3", password, "ADMIN3@ADMIN.COM", Role.ADMIN, 10000000, true);
        userRepository.save(user3);
    }

    /* 회원가입 */
    @Transactional
    public void userJoin(UserDto.Request dto) {

        dto.setPassword(encoder.encode(dto.getPassword()));

        userRepository.save(dto.toEntity());
    }

    /* 회원가입 시, 유효성 검사 및 중복 체크 */
    @Transactional(readOnly = true) //더티체킹 스킵
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사, 중복 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    /* 회원수정 (dirty checking) */
    @Transactional
    public void modify(UserDto.Request dto) {
        User user = userRepository.findById(dto.toEntity().getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        String encPassword = encoder.encode(dto.getPassword());
        user.modify(dto.getNickname(), encPassword);
    }

    @Transactional(readOnly = true)
    public Page<User> findUserList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public String findByNickname(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        return user.getNickname();
    }

    @Transactional(readOnly = true)
    public boolean userFind(UserDto.Find dto) {
        return userRepository.findByUsername(dto.getUsername()).isPresent()
                && userRepository.findByEmail(dto.getEmail()).isPresent();
    }

    @Transactional(readOnly = true)
    public long findUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        return user.getId();
    }

    @Transactional
    public void passwordModify(long id, UserDto.Password dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        user.setUserPassword(encoder.encode(dto.getPassword1()));
    }

    @Transactional(readOnly = true)
    public boolean passwordCheck(long id, String password1) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() && encoder.matches(password1, user.get().getPassword());
    }
}