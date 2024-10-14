package hexagonal_test.hexagonal.user.domain;

import hexagonal_test.hexagonal.user.controller.request.SignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

public class UserTest {

    @Test
    @DisplayName("SignupRequest객체로 유저 생성")
    void test1() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .email("test@test.com")
                .password1("1234")
                .password2("1234")
                .username("test")
                .build();

        // when
        User user = User.from(signupRequest, "test@test.com1234");

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getPassword()).isEqualTo("test@test.com1234");
    }

    @Test
    @DisplayName("로그인")
    void test2() {
        // given
        User user = User.builder()
                .id(1L)
                .email("nmkk1234@naver.com")
                .username("nmkk1234")
                .password("nmkk1234@naver.com1234")
                .build();

        // when
        User loginUser = user.login();

        // then
        assertThat(loginUser.getPassword()).isEqualTo("nmkk1234@naver.com1234");
    }
    @Test
    @DisplayName("패스워드 일치 확인")
    void test3() {
        // given
        String password1 = "1234";
        String password2 = "1234";
        // when
        // then
        assertThatNoException().isThrownBy(() -> User.checkPassword(password1, password2));
    }

}
