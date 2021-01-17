package projects.portfoliodemo.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import projects.portfoliodemo.security.CustomUserDetailsService;
import projects.portfoliodemo.service.UserService;

@DisplayName("Web Registration Specification")
@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @MockBean
    CustomUserDetailsService mockCUDS;

    @MockBean
    UserService userService;

}