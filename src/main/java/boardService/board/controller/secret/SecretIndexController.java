package boardService.board.controller.secret;

import boardService.board.domain.secret.SecretPosts;
import boardService.board.dto.post.CommentDto;
import boardService.board.dto.secret.SecretCommentDto;
import boardService.board.dto.secret.SecretPostsDto;
import boardService.board.dto.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.secret.SecretPostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class SecretIndexController {

    private final SecretPostsService secretPostsService;

    @GetMapping("/secrets")
    public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @LoginUser UserDto.Response user){
        Page<SecretPosts> pages = secretPostsService.pageList(pageable);
        if(user != null){
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", pages);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", pages.hasNext());
        model.addAttribute("hasPrev", pages.hasPrevious());
        return "/secrets/index";
    }

    @GetMapping("/secrets/posts/write")
    public String write(@LoginUser UserDto.Response user, Model model){
        if(user != null){
            model.addAttribute("user", user);
        }
        return "posts-write";
    }

    @GetMapping("/secrets/posts/read/{id}")
    public String read(@PathVariable long id, @LoginUser UserDto.Response dto, Model model) {
        SecretPostsDto.Response secretPosts = secretPostsService.findById(id);
        List<SecretCommentDto.Response> secretComment = secretPosts.getComments();

        if (secretComment != null && !secretComment.isEmpty()) {
            model.addAttribute("comments", secretComment);
        }
        if (dto != null) {
            model.addAttribute("user", dto);
            if (secretPosts.getUserId().equals(dto.getId())) {
                model.addAttribute("writer", true);
            }
            if (secretComment.stream().anyMatch(r -> r.getUserId().equals(dto.getId()))) {
                model.addAttribute("isWriter", true);
            }
        }
        if(dto != null){
            if(!Objects.equals(secretPosts.getUserId(), dto.getId())) {
                secretPostsService.updateView(id); //자신이 조회하는건 조회수 증가x
            }
        }
        model.addAttribute("posts", secretPosts);
        return "posts-read";
    }

    @GetMapping("/secrets/posts/update/{id}")
    public String update(@PathVariable long id, @LoginUser UserDto.Response user, Model model){
        SecretPostsDto.Response secretPosts = secretPostsService.findById(id);
        if(user != null){
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", secretPosts);
        return "posts-update";
    }

    @GetMapping("/secrets/posts/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         @LoginUser UserDto.Response user){
        Page<SecretPosts> pages = secretPostsService.search(keyword, pageable);
        if(user != null){
            model.addAttribute("user", user);
        }

        model.addAttribute("searchList", pages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", pages.hasNext());
        model.addAttribute("hasPrev", pages.hasPrevious());
        return "posts-search";
    }
}
