package boardService.board.controller.report;

import boardService.board.domain.report.Report;
import boardService.board.domain.user.Role;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.post.PostsService;
import boardService.board.service.report.ReportService;
import boardService.board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;
    private final PostsService postsService;

    @GetMapping("/posts/{pageNum}/report")
    public String reportPage(@LoginUser UserDto.Response user, Model model, @PathVariable long pageNum){
        if(user != null){
            String nickname = postsService.findByPageNum(pageNum);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("nickname", nickname);
            model.addAttribute("user", user);
        }
        return "/admin/report-write";
    }

    @GetMapping("/admin/reportList")
    public String reportList(@LoginUser UserDto.Response user, Model model, @PageableDefault (sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Report> reports = reportService.findAll(pageable);
        if(user == null || !user.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        model.addAttribute("size", reports.getTotalElements());
        model.addAttribute("reports", reports);
        model.addAttribute("user", user);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", reports.hasNext());
        model.addAttribute("hasPrev", reports.hasPrevious());
        return "/admin/report-list";
    }

    @GetMapping("/admin/report/{id}")
    public String reportSelect(@LoginUser UserDto.Response user, Model model, @PathVariable long id){
        if(user == null || !user.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        Report report = reportService.findReport(id);
        model.addAttribute("report", report);
        model.addAttribute("user", user);
        return "/admin/report-page";
    }
}
