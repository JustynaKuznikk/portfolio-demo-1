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

import static org.junit.jupiter.api.Assertions.*;

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

        @DisplayName("- should save user with provided data and set default values, and return user id")
        @Test
        void test1() {
            RegisterUserCommand command = CommandProvider.registerUserCommand("duke", "pass");
            User convertedUser = User.builder()
                    .username("duke")
                    .password("pass")
                    .build();

            Mockito.when(userConverter.from(command)).thenReturn(convertedUser);
            Mockito.when(passwordEncoder.encode("pass")).thenReturn("encoded");
            Mockito.when(userRepository.save(userCaptor.capture()))
                    .thenAnswer(invocation -> {
                        User userToSave = invocation.getArgument(0, User.class);
                        userToSave.setId(99L);
                        return userToSave;
                    });

            Long result = cut.create(command);

            User savedUser = userCaptor.getValue();
            assertNotNull(savedUser);
            assertEquals("duke", savedUser.getUsername());
            assertTrue(savedUser.getActive());
            Assertions.assertThat(savedUser.getRoles()).containsOnly("ROLE_USER");
            assertEquals("encoded", savedUser.getPassword());

            assertEquals(99L, result);
        }

        @DisplayName("- should raise an error when user with same username already exists")
        @Test
        void test2() {
            RegisterUserCommand command = CommandProvider.registerUserCommand("joe", "pass");
            User user = User.builder().username("joe").password("pass").build();

            Mockito.when(userConverter.from(command)).thenReturn(user);
            Mockito.when(userRepository.existsByUsername("joe")).thenReturn(true);

            Assertions.assertThatThrownBy(() -> cut.create(command))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("user with same username already exists")
                    .hasMessageNotContaining("joe")
                    .hasNoCause();

            Mockito.verifyNoInteractions(passwordEncoder);
            Mockito.verifyNoMoreInteractions(userRepository);


        }
    }


}