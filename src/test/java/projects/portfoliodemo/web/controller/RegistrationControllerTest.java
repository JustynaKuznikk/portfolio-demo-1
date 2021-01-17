package projects.portfoliodemo.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projects.portfoliodemo.security.CustomUserDetailsService;
import projects.portfoliodemo.service.UserService;
import projects.portfoliodemo.web.command.RegisterUserCommand;

@DisplayName("Registration Specification: /register")
@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @MockBean
    CustomUserDetailsService mockCUDS;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    String endpoint = "/register";

    // MockMvcRequestBuilders -> do tworzenia żądań
    // MockMvc

    @DisplayName("1. On GET request")
    @Nested
    class GetRequest {

        @DisplayName("- should prepare registration view with data in model")
        @Test
        void test1() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("registration/form"))
                    .andExpect(MockMvcResultMatchers.model().attribute("data", new RegisterUserCommand()));
        }

        @DisplayName("- should allow anonymous user")
        @Test
        void test2() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                    .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

    }


}