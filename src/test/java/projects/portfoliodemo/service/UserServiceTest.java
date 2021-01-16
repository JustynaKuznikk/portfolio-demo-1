package projects.portfoliodemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import projects.portfoliodemo.converter.UserConverter;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.domain.repository.UserRepository;
import projects.portfoliodemo.provider.CommandProvider;
import projects.portfoliodemo.web.command.RegisterUserCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("User processes specification")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserConverter userConverter;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService cut;

    @BeforeEach
    void setUp() {
        //
    }

    @DisplayName("1. Creating new user")
    @Nested
    class CreateUser {

        @DisplayName("- should save user and return they id")
        @Test
        void test1() {
            Mockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                    .thenAnswer(invocation -> {
                        User userToSave = invocation.getArgument(0, User.class);
                        userToSave.setId(99L);
                        return userToSave;
                    });

            RegisterUserCommand command = CommandProvider.registerUserCommand("duke", "pass");

            Long result = cut.create(command);

            verify(userRepository, times(1)).save(any(User.class));

            assertEquals(99L, result);
        }

    }


}