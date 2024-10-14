package hexagonal_test.hexagonal.user.service;

import hexagonal_test.hexagonal.common.domain.exception.AlreadyExistsEmailException;
import hexagonal_test.hexagonal.common.domain.exception.PasswordNotMatchException;
import hexagonal_test.hexagonal.common.domain.exception.UserNotFound;
import hexagonal_test.hexagonal.common.service.SessionManager;
import hexagonal_test.hexagonal.mock.FakePasswordEncoder;
import hexagonal_test.hexagonal.mock.FakeUserRepository;
import hexagonal_test.hexagonal.user.controller.request.LoginRequest;
import hexagonal_test.hexagonal.user.controller.request.SignupRequest;
import hexagonal_test.hexagonal.user.domain.User;
import hexagonal_test.hexagonal.user.infrastructure.PasswordEncoderImpl;
import hexagonal_test.hexagonal.user.service.port.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.beans.Beans.isInstanceOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
        this.userService = UserService.builder()
                .userRepository(fakeUserRepository)
                .passwordEncoder(fakePasswordEncoder)
                .sessionManager(new SessionManager())
                .build();

        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("nmkk1234@naver.com")
                .username("nmkk1234")
                .password(fakePasswordEncoder.encode("nmkk1234@naver.com", "1234"))
                .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("nmkk123@naver.com")
                .username("nmkk123")
                .password(fakePasswordEncoder.encode("nmkk123@naver.com", "123"))
                .build());
    }
    // 회원가입 성공
    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .email("test@test.com")
                .password1("1234")
                .password2("1234")
                .username("test")
                .build();
        // when
        User result = userService.signup(signupRequest);
        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPassword()).isEqualTo("test@test.com1234");
    }

    // 이미 존재하는 회원이면 에러를 반환한다
    @Test
    @DisplayName("이미 존재하는 회원이면 에러를 반환한다")
    void test2() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .email("nmkk1234@naver.com")
                .password1("1234")
                .password2("1234")
                .username("test")
                .build();
        // when
        // then
        assertThatThrownBy(()->{
            userService.signup(signupRequest);
        }).isInstanceOf(AlreadyExistsEmailException.class);
    }

    // 로그인 성공
    @Test
    @DisplayName("로그인 성공")
    void test3() {
        // given
        LoginRequest loginRequest = LoginRequest
                .builder()
                .email("nmkk1234@naver.com")
                .password("1234")
                .build();
        // when
        User user = userService.login(loginRequest);
        // then
        assertThat(user.getEmail()).isEqualTo("nmkk1234@naver.com");
        assertThat(user.getPassword()).isEqualTo("nmkk1234@naver.com1234");
    }
    @Test
    @DisplayName("로그인시 비밀번호가 틀리면 에러를 반환한다")
    void test4() {
        // given
        LoginRequest loginRequest = LoginRequest
                .builder()
                .email("nmkk1234@naver.com")
                .password("123")
                .build();
        // when
        // then
        assertThatThrownBy(()->{
            userService.login(loginRequest);
        }).isInstanceOf(PasswordNotMatchException.class);
    }
    @Test
    @DisplayName("존재하지 않는 회원이면 에러를 반환한다")
    void test5() {
        // given
        LoginRequest loginRequest = LoginRequest
                .builder()
                .email("nmkk123455@naver.com")
                .password("123")
                .build();
        // when
        // then
        assertThatThrownBy(()->{
            userService.login(loginRequest);
        }).isInstanceOf(UserNotFound.class);
    }

    //
    @Test
    @DisplayName("로그아웃 성공")
    void test6() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        // When
        when(request.getSession(false)).thenReturn(session);
        userService.logout(request);  // logout 호출
        // Then
        verify(session, times(1)).invalidate();
    }

}
