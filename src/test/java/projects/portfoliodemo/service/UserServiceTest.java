package projects.portfoliodemo.service;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import projects.portfoliodemo.converter.UserConverter;
import projects.portfoliodemo.domain.repository.UserRepository;
import projects.portfoliodemo.web.command.RegisterUserCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        @DisplayName("- should return id of saved user")
        @Test
        void test1() {
            RegisterUserCommand command = new RegisterUserCommand();
            command.setUsername("duke");
            command.setPassword("pass");

            Long result = cut.create(command);

            assertNotNull(result);
            assertThat(result)
                    .isNotNull()
                    .isPositive();
            MatcherAssert.assertThat(result, allOf(notNullValue(), greaterThan(0L)));
        }

    }


}