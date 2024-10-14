package hexagonal_test.hexagonal.mock;

import hexagonal_test.hexagonal.user.service.port.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String email, String password) {
        return String.format("%s%s", email, password);
    }
}
