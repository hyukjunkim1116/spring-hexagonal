package hexagonal_test.hexagonal.user.domain;

import hexagonal_test.hexagonal.common.domain.exception.PasswordNotMatchException;
import hexagonal_test.hexagonal.user.controller.request.LoginRequest;
import hexagonal_test.hexagonal.user.controller.request.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final String username;

    @Builder
    public User(Long id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static User from(SignupRequest signupRequest, String encodedPassword) {
        return User.builder()
                .email(signupRequest.getEmail())
                .username(signupRequest.getUsername())
                .password(encodedPassword)
                .build();
    }

    public User login() {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .username(username)
                .build();
    }

    public static void checkPassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new PasswordNotMatchException();
        }
    }


}
