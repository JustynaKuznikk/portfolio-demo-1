package projects.portfoliodemo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.portfoliodemo.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
