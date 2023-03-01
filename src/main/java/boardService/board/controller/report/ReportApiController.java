package boardService.board.controller.report;

import boardService.board.domain.user.Role;
import boardService.board.dto.report.ReportDto;
import boardService.board.dto.user.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReportApiController {

    private final ReportService reportService;

    @PostMapping("/posts/{pageNum}/report")
    public ResponseEntity<?> postReport(@PathVariable long pageNum, @RequestBody @Valid ReportDto.Request dto, @LoginUser UserDto.Response user){
        if(user == null){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        return ResponseEntity.ok(reportService.postReport(pageNum, dto, user.getId()));
    }

    @PutMapping("/admin/report/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable long id, @LoginUser UserDto.Response user){
        if(user == null || !user.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        reportService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/report/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable long id, @LoginUser UserDto.Response user){
        if(user == null || !user.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/report/deletePoint/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable long id, @LoginUser UserDto.Response user){
        if(user == null || !user.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        reportService.deletePoint(id);
        return ResponseEntity.noContent().build();
    }
}
