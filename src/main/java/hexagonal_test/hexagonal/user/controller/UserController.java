package hexagonal_test.hexagonal.user.controller;


import hexagonal_test.hexagonal.common.controller.web.argumentresolver.Login;
import hexagonal_test.hexagonal.common.service.SessionManager;
import hexagonal_test.hexagonal.user.controller.request.LoginRequest;
import hexagonal_test.hexagonal.user.controller.request.SignupRequest;
import hexagonal_test.hexagonal.user.controller.request.UserInfo;
import hexagonal_test.hexagonal.user.domain.User;
import hexagonal_test.hexagonal.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
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
@Builder
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final SessionManager sessionManager;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Validated SignupRequest signupRequest) {
        User user = userService.signup(signupRequest);
        return new ResponseEntity<>("가입 완료", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request) {
       User user = userService.login(loginRequest);
       UserInfo userInfo = UserInfo.from(user);
       sessionManager.setSession(request, userInfo);
       return new ResponseEntity<>("로그인 완료", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }

    @PostMapping("/test")
    public Long test(@Login UserInfo loginUser) {
        log.info("test");
        return loginUser.getId();
    }

}
