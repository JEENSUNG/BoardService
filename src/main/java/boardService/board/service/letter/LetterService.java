package boardService.board.service.letter;

import boardService.board.domain.letter.Letter;
import boardService.board.domain.user.User;
import boardService.board.dto.letter.LetterDto;
import boardService.board.repository.letter.LetterRepository;
import boardService.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendLetter(long to, long from, LetterDto.Request dto) {
        User taken = userRepository.findById(to).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        User send = userRepository.findById(from).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        dto.setSendUsername(send.getNickname());
        dto.setTakenUsername(taken.getNickname());
        dto.setToUser(to);
        dto.setFromUser(from);
        letterRepository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<Letter> findSendList(Long id, Pageable pageable) {
        userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        return letterRepository.findAllByFromUser(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Letter> findTakenList(Long id, Pageable pageable) {
        userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        return letterRepository.findAllByToUser(id, pageable);
    }

    @Transactional(readOnly = true)
    public Letter findLetter(long id) {
        return letterRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없습니다."));
    }
}
