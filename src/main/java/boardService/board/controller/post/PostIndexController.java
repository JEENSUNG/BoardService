package boardService.board.controller.post;

import boardService.board.domain.post.Posts;
import boardService.board.dto.post.CommentDto;
import boardService.board.dto.post.PostsDto;
import boardService.board.dto.UserDto;
import boardService.board.security.auth.LoginUser;
import boardService.board.service.post.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class PostIndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                        @LoginUser UserDto.Response user){
        Page<Posts> pages = postsService.pageList(pageable);
        if(user != null){
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", pages);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", pages.hasNext());
        model.addAttribute("hasPrev", pages.hasPrevious());
        return "posts/index";
    }

    @GetMapping("/posts/write")
    public String write(@LoginUser UserDto.Response user, Model model){
        if(user != null){
            model.addAttribute("user", user);
        }
        return "posts/posts-write";
    }

    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable long id, @LoginUser UserDto.Response dto, Model model) {
        PostsDto.Response post = postsService.findById(id);
        List<CommentDto.Response> comments = post.getComments();

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }
        if (dto != null) {
            model.addAttribute("user", dto);
            if (post.getUserId().equals(dto.getId())) {
                model.addAttribute("writer", true);
            }
            if (comments.stream().anyMatch(r -> r.getUserId().equals(dto.getId()))) {
                model.addAttribute("isWriter", true);
            }
        }
        if(dto != null){
            if(!Objects.equals(post.getUserId(), dto.getId())) {
                postsService.updateView(id); //자신이 조회하는건 조회수 증가x
            }
        }
        model.addAttribute("posts", post);
        return "posts/posts-read";
    }

    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable long id, @LoginUser UserDto.Response user, Model model){
        PostsDto.Response dto = postsService.findById(id);
        if(user != null){
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", dto);
        return "posts/posts-update";
    }

    @GetMapping("/posts/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         @LoginUser UserDto.Response user){
        Page<Posts> pages = postsService.search(keyword, pageable);
        if(user != null){
            model.addAttribute("user", user);
        }

        model.addAttribute("searchList", pages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", pages.hasNext());
        model.addAttribute("hasPrev", pages.hasPrevious());
        return "posts/posts-search";
    }
}
