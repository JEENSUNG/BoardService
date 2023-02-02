package boardService.board.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpSession httpSession;

    public CustomAuthFailureHandler(HttpSession httpSession){
        this.httpSession = httpSession;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        if(exception instanceof BadCredentialsException){
            errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다. 다시 시도해 주세요.";
        }else if(exception instanceof InternalAuthenticationServiceException){
            errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의해주세요";
        }else if(exception instanceof UsernameNotFoundException){
            errorMessage = "존재하지 않는 사용자입니다. 회원가입 진행 후 로그인 해주세요.";
        }else if(exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의해 주세요.";
        }else{
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의해 주세요.";
        }
        errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/auth/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
        httpSession.invalidate();
    }
}
