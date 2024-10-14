package hexagonal_test.hexagonal.user.infrastructure;

import hexagonal_test.hexagonal.user.domain.User;
import hexagonal_test.hexagonal.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }

    @Override
    public void deleteAll() {
        userJpaRepository.deleteAll();
    }

    @Override
    public Long count() {
        return userJpaRepository.count();
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream().map(UserEntity::toModel).toList();
    }
}
