package projects.portfoliodemo.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.provider.CommandProvider;
import projects.portfoliodemo.web.command.RegisterUserCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
            RegisterUserCommand command = CommandProvider.registerUserCommand("duke", "s3cr3t");

            // when
            User result = cut.from(command);

            // then
            assertThatProvidedValuesAreSet(result, "duke", "s3cr3t");
            assertThatNothingThanExpectedIsSet(result, "username", "password", "roles", "active");
        }

        @DisplayName("- should convert to user with default values set")
        @Test
        void test2() {
            RegisterUserCommand command = CommandProvider.registerUserCommand("any", "any");

            User result = cut.from(command);

            assertThatActiveIsFalseByDefault(result);
            assertThatRolesAreEmptyByDefault(result);
        }

        @DisplayName("- should raise an error when no data provided")
        @Test
        void test3() {
            assertThatRaiseErrorWithMessage(null, UnconvertibleDataException.class, "Cannot convert from null");
        }

        private void assertThatRaiseErrorWithMessage(RegisterUserCommand command, Class<? extends Exception> klass, String message) {
            assertThatThrownBy(() -> cut.from(command))
                    .isInstanceOf(klass)
                    .hasMessageContaining(message)
                    .hasNoCause();
        }

        private void assertThatActiveIsFalseByDefault(User result) {
            assertEquals(false, result.getActive());
        }

        private void assertThatRolesAreEmptyByDefault(User result) {
            assertThat(result.getRoles())
                    .isEmpty();
        }

        private void assertThatProvidedValuesAreSet(User result, String username, String password) {
            assertNotNull(result);
            assertEquals(username, result.getUsername());
            assertEquals(password, result.getPassword());
        }

        private void assertThatNothingThanExpectedIsSet(User result, String... properties) {
            assertThat(result)
                    .hasAllNullFieldsOrPropertiesExcept(properties);
        }

    }


}