package projects.portfoliodemo.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.web.command.RegisterUserCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("User converting specification")
class UserConverterTest {

    UserConverter cut;  // Class under test

    @BeforeEach
    void setUp() {
        cut = new UserConverter();
    }

    /*

        1. Pierwszy test: test optymistyczny; standardowe użycie metody, w której wszystko działa
        2. Przypadki alternatywne: gdy warunki zachowują się w inny sposób ---> 100% pokrycia kodu
        3. Przypadki błędne: gdy dane powodują wyjątki

     */

    @DisplayName("1. Converting from registration command")
    @Nested
    class ConvertFromRegisterUserCommand {

        @DisplayName("- should convert to user with all provided data")
        @Test
        void test1() {
            // given
            RegisterUserCommand command = new RegisterUserCommand();
            command.setUsername("duke");
            command.setPassword("s3cr3t");

            // when
            User result = cut.from(command);

            // then
            assertNotNull(result);
            assertEquals("duke", result.getUsername());
            assertEquals("s3cr3t", result.getPassword());

            Assertions.assertThat(result)
                    .hasAllNullFieldsOrPropertiesExcept("username", "password", "roles");
            Assertions.assertThat(result.getRoles())
                    .isEmpty();
        }

    }


}