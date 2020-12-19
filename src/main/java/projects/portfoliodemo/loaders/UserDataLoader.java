package projects.portfoliodemo.loaders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.domain.model.UserDetails;
import projects.portfoliodemo.domain.repository.UserRepository;

import java.time.LocalDate;
import java.util.Set;

@Component
@Profile("heroku")
@Slf4j
@RequiredArgsConstructor
public class UserDataLoader implements DataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Transactional
    public void loadData() {
        User duke = User.builder()
                .username("duke@gmail.com")
                .password(passwordEncoder.encode("secret"))
                .active(true)
                .roles(Set.of("ROLE_USER"))
                .build();
        duke.setDetails(UserDetails.builder()
                .user(duke)
                .firstName("Duke")
                .lastName("Alfonso")
                .birthDate(LocalDate.of(1970, 4, 17))
                .build());

        userRepository.save(duke);
        log.debug("Saved user: {}", duke);
    }

}
