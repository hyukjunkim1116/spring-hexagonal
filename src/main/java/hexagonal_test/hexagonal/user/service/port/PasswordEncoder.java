package hexagonal_test.hexagonal.user.service.port;

public interface PasswordEncoder {
    String encode(String email, String password);
}
