package projects.portfoliodemo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projects.portfoliodemo.service.UserService;
import projects.portfoliodemo.web.command.RegisterUserCommand;

import javax.validation.Valid;

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
    public String process(@ModelAttribute("data") @Valid RegisterUserCommand command, BindingResult bindings) {
        if (bindings.hasErrors()) {
            return "registration/form";
        }
        userService.create(command);
        return "redirect:/login";
    }
}
