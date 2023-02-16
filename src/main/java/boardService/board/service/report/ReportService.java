package boardService.board.service.report;

import boardService.board.domain.post.Posts;
import boardService.board.domain.report.Report;
import boardService.board.domain.user.Role;
import boardService.board.domain.user.User;
import boardService.board.dto.report.ReportDto;
import boardService.board.repository.post.PostsRepository;
import boardService.board.repository.report.ReportRepository;
import boardService.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public long postReport(long pageNum, ReportDto.Request dto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        Posts page = postsRepository.findById(pageNum).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        dto.setUsername(user.getNickname());
        dto.setNickname(page.getUser().getNickname());
        dto.setUser(user);
        dto.setToUser(page.getUser().getId());
        Report report = dto.toEntity();
        reportRepository.save(report);
        return report.getId();
    }

    @Transactional(readOnly = true)
    public Page<Report> findAll(Pageable pageable) {
        return reportRepository.findAllByIsRemoved(pageable, true);
    }

    @Transactional(readOnly = true)
    public Report findReport(long id) {
        return reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 게시글입니다."));
    }

    @Transactional
    public void deleteRole(long id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 게시글입니다."));
        User user = userRepository.findById(report.getToUser()).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        if(user.getRole() == Role.USER_VIP){
            user.setRole(Role.USER);
        }else if(user.getRole() == Role.SOCIAL_VIP){
            user.setRole(Role.SOCIAL);
        }
        report.setIsRemoved(false);
        userRepository.save(user);
    }

    @Transactional
    public void deleteReport(long id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 게시글입니다."));
        report.setIsRemoved(false);
    }

    @Transactional
    public void deletePoint(long id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 게시글입니다."));
        User user = userRepository.findById(report.getToUser()).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원입니다."));
        if(user.getPoint() >= 0)
            user.setPoint(0);
        report.setIsRemoved(false);
        userRepository.save(user);
    }
}
