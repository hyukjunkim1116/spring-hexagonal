package hexagonal_test.hexagonal.user.controller;

import hexagonal_test.hexagonal.mock.TestContainer;
import hexagonal_test.hexagonal.user.controller.request.LoginRequest;
import hexagonal_test.hexagonal.user.controller.request.SignupRequest;
import hexagonal_test.hexagonal.user.controller.request.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {


    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        SignupRequest signupRequest = SignupRequest.builder()
                .email("kok202@kakao.com")
                .username("kok202")
                .password1("1234")
                .password2("1234")
                .build();
        // when
        ResponseEntity<String> result = testContainer.userController.signup(signupRequest);


        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isEqualTo("가입 완료");

    }

    // 회원가입이 실패하면 400을 반환한다
    @Test
    @DisplayName("회원가입 실패시 400반환")
    void test2() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        SignupRequest signupRequest = SignupRequest.builder()
                .email("kok202@kakao.com")
                .username("kok202")
                .password1("1234")
                .password2("123")
                .build();
        // when
        // then
        assertThatThrownBy(() -> {
            testContainer.userController.signup(signupRequest);
        });
    }

    @Test
    @DisplayName("로그아웃 성공")
    void test3() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        // when
        when(mockRequest.getSession(false)).thenReturn(null);
        ResponseEntity<String> result = testContainer.userController.logout(mockRequest);
        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }
}
