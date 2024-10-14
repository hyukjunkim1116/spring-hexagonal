package hexagonal_test.hexagonal.mock;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import hexagonal_test.hexagonal.common.service.SessionManager;
import hexagonal_test.hexagonal.user.controller.UserController;
import hexagonal_test.hexagonal.user.service.UserService;
import hexagonal_test.hexagonal.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final UserRepository userRepository;
    public final UserController userController;
    public final UserService userService;

    @Builder
    public TestContainer() {
        this.userRepository = new FakeUserRepository();
        this.userService = UserService.builder()
            .userRepository(this.userRepository)
            .passwordEncoder(new FakePasswordEncoder())
            .sessionManager(new SessionManager())
            .build();
        this.userController = UserController.builder()
            .userService(this.userService)
            .build();
    }
}
