package projects.portfoliodemo.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projects.portfoliodemo.security.CustomUserDetailsService;
import projects.portfoliodemo.service.UserService;

@DisplayName("Web Registration Specification: /register")
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

    @DisplayName("- should get registration view on GET request")
    @Test
    void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration/form"));
    }


}