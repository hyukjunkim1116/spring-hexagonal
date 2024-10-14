package hexagonal_test.hexagonal.user.service;

import hexagonal_test.hexagonal.common.domain.exception.AlreadyExistsEmailException;
import hexagonal_test.hexagonal.common.domain.exception.PasswordNotMatchException;
import hexagonal_test.hexagonal.common.domain.exception.UserNotFound;
import hexagonal_test.hexagonal.common.service.SessionManager;
import hexagonal_test.hexagonal.user.controller.request.LoginRequest;
import hexagonal_test.hexagonal.user.controller.request.SignupRequest;
import hexagonal_test.hexagonal.user.domain.User;
import hexagonal_test.hexagonal.user.service.port.PasswordEncoder;
import hexagonal_test.hexagonal.user.service.port.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;


@Service
@Builder
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    @Transactional
    public User signup(SignupRequest signupRequest) {
        userRepository.findByEmail(signupRequest.getEmail()).ifPresent(user -> {throw new AlreadyExistsEmailException();}
        );
        User.checkPassword(signupRequest.getPassword1(), signupRequest.getPassword2());
        String encryptedPassword = passwordEncoder.encode(signupRequest.getEmail(), signupRequest.getPassword1());
        User user = User.from(signupRequest, encryptedPassword);
        user = userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(UserNotFound::new);

        String encryptedPassword = passwordEncoder.encode(loginRequest.getEmail(), loginRequest.getPassword());
        User.checkPassword(encryptedPassword, user.getPassword());
        return user.login();
    }

    public void logout(HttpServletRequest request) {
        sessionManager.invalidate(request);
    }
}


