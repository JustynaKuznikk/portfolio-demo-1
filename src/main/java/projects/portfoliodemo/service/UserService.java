package projects.portfoliodemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.portfoliodemo.converter.UserConverter;
import projects.portfoliodemo.data.user.UserSummary;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.domain.repository.UserRepository;
import projects.portfoliodemo.web.command.EditUserCommand;
import projects.portfoliodemo.web.command.RegisterUserCommand;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long create(RegisterUserCommand registerUserCommand) {
        User user = userConverter.from(registerUserCommand);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalStateException("User with same username already exists");
        }
        user.setActive(true);
        user.getRoles().add("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public UserSummary getCurrentUserSummary() {
        log.debug("Pobieranie danych użytkownika aktualnie zalogowanego");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.getAuthenticatedUser(username);
        UserSummary summary = userConverter.toUserSummary(user);
        log.debug("Podsumowanie danych użytkownika: {}", summary);

        return summary;
    }

    @Transactional
    public boolean edit(EditUserCommand editUserCommand) {
        log.debug("Dane do edycji użytkownika: {}", editUserCommand);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getAuthenticatedUser(username);
        log.debug("Edycja użytkownika: {}", user);

        user = userConverter.from(editUserCommand, user);
        log.debug("Zmodyfikowane dane użytkownika: {}", user.getDetails());
        return true;
    }
}
