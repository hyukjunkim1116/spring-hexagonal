package hexagonal_test.hexagonal.user.controller.request;

import hexagonal_test.hexagonal.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserInfo {

    private final Long id;
    private final String email;
    private final String username;

    @Builder
    public UserInfo(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public static UserInfo from(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
