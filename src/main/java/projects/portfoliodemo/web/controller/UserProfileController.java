package projects.portfoliodemo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projects.portfoliodemo.data.user.UserSummary;
import projects.portfoliodemo.service.UserService;

@Controller
@RequestMapping("/profile")
@Slf4j
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping
    public String getProfilePage(Model model) {
        UserSummary summary = userService.getCurrentUserSummary();
        model.addAttribute("userSummary", summary);
        return "user/profile";
    }
}
