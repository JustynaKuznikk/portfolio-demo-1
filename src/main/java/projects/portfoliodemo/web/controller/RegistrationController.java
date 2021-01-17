package projects.portfoliodemo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projects.portfoliodemo.service.UserService;
import projects.portfoliodemo.web.command.RegisterUserCommand;

@Controller
@RequestMapping("/register")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String preparePage(Model model) {
        model.addAttribute("data", new RegisterUserCommand());
        return "registration/form";
    }

    @PostMapping
    public String process(RegisterUserCommand command) {
        userService.create(command);
        return "redirect:/login";
    }
}
