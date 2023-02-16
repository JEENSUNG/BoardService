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
    public long sendLetter(long to, long from, LetterDto.Request dto, long pageNum) {
        User taken = userRepository.findById(to).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        User send = userRepository.findById(from).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        dto.setToUser(to);
        dto.setFromUser(from);
        dto.setTakenUsername(taken.getNickname());
        dto.setSendUsername(send.getNickname());
        dto.setPageNum(pageNum);
        Letter letter = dto.toEntity();
        letterRepository.save(letter);
        return letter.getId();
    }

    @Transactional(readOnly = true)
    public Page<Letter> findSendList(long id, Pageable pageable) {
        userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        return letterRepository.findAllByToUser(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Letter> findTakenList(long id, Pageable pageable) {
        userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        return letterRepository.findAllByFromUser(id, pageable);
    }

    @Transactional(readOnly = true)
    public Letter findLetter(long id) {
        return letterRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없습니다."));
    }

    @Transactional
    public void deleteToUserLetter(long id) {
        Letter letter = letterRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없습니다."));
        letter.setToUser(0);
    }
    @Transactional
    public void deleteFromUserLetter(long id) {
        Letter letter = letterRepository.findById(id).orElseThrow(() -> new FindException("찾을 수 없습니다."));
        letter.setFromUser(0);
    }
}
