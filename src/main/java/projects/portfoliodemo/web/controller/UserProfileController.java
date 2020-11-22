package projects.portfoliodemo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projects.portfoliodemo.data.user.UserSummary;
import projects.portfoliodemo.service.UserService;
import projects.portfoliodemo.web.command.EditUserCommand;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@Slf4j
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping
    public String getProfilePage(Model model) {
        UserSummary summary = userService.getCurrentUserSummary();
        EditUserCommand editUserCommand = createEditUserCommand(summary);
        model.addAttribute(summary);
        model.addAttribute(editUserCommand);
        return "user/profile";
    }

    private EditUserCommand createEditUserCommand(UserSummary summary) {
        return EditUserCommand.builder()
                .firstName(summary.getFirstName())
                .lastName(summary.getLastName())
                .birthDate(summary.getBirthDate())
                .build();
    }

    @PostMapping("/edit")
    public String editUserProfile(@Valid EditUserCommand editUserCommand, BindingResult bindings) {
        // TODO Do samodzielnej implementacji jako ćwiczenie ;)
        return "redirect:/profile";
    }
}
