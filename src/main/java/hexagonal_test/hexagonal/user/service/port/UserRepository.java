package hexagonal_test.hexagonal.user.service.port;

import hexagonal_test.hexagonal.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    User save(User user);

    void deleteAll();

    Long count();

    List<User> findAll();
}
