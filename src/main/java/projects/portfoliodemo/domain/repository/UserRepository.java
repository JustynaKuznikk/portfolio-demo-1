package projects.portfoliodemo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.portfoliodemo.domain.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
