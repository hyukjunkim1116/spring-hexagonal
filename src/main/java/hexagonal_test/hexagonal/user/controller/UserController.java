package hexagonal_test.hexagonal.user.controller;

import com.example.qna_backend.config.argumentresolver.Login;
import com.example.qna_backend.user.dto.LoginRequest;
import com.example.qna_backend.user.dto.SignupRequest;
import com.example.qna_backend.user.dto.UserInfo;
import com.example.qna_backend.user.service.UserService;
import com.example.qna_backend.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Validated SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return new ResponseEntity<>("가입 완료", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request) {
        UserInfo loginUser = userService.login(loginRequest);
        setSession(request,loginUser);
        return new ResponseEntity<>("로그인 완료", HttpStatus.OK);
    }

    private void setSession(HttpServletRequest request,UserInfo loginUser) {
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
        session.setMaxInactiveInterval(1800);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        userService.logout(request);
        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }
    @PostMapping("/test")
    public String test(@Login UserInfo loginUser) {
        log.info("test");
        log.info("loginUser : {}", loginUser.getId());
        return "ok";
    }
}
