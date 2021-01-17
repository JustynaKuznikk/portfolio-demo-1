package projects.portfoliodemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import projects.portfoliodemo.converter.UserConverter;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.domain.repository.UserRepository;
import projects.portfoliodemo.provider.CommandProvider;
import projects.portfoliodemo.web.command.RegisterUserCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("User processes specification")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserConverter userConverter;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Captor
    ArgumentCaptor<User> userCaptor;

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
            Mockito.when(userConverter.from(any(RegisterUserCommand.class))).thenReturn(new User());
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

        @DisplayName("- should save user with provided data")
        @Test
        void test2() {
            RegisterUserCommand command = CommandProvider.registerUserCommand("duke", "pass");
            User expectedToSave = User.builder()
                    .username("duke")
                    .password("pass")
                    .build();

            Mockito.when(userConverter.from(command)).thenReturn(expectedToSave);

            cut.create(command);

            Mockito.verify(userRepository, Mockito.atLeastOnce()).save(userCaptor.capture());
            User savedUser = userCaptor.getValue();

            Assertions.assertThat(savedUser)
                    .isEqualToComparingOnlyGivenFields(expectedToSave, "username", "password");
        }

        @DisplayName("- should save user as active with user role and encoded password")
        @Test
        void test3() {
            RegisterUserCommand command = CommandProvider.registerUserCommand("duke", "pass");
            User expectedToSave = User.builder()
                    .username("duke")
                    .password("pass")
                    .build();

            Mockito.when(passwordEncoder.encode("pass")).thenReturn("encoded");
            Mockito.when(userConverter.from(command)).thenReturn(expectedToSave);

            cut.create(command);

            Mockito.verify(userRepository, atLeastOnce()).save(userCaptor.capture());
            User savedUser = userCaptor.getValue();

            assertTrue(savedUser.getActive());
            Assertions.assertThat(savedUser.getRoles()).containsOnly("ROLE_USER");
            assertEquals("encoded", savedUser.getPassword());
        }

    }


}