package projects.portfoliodemo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@Slf4j
@RequiredArgsConstructor
public class UserProfileController {

    @GetMapping
    public String getProfilePage(Model model) {
        return "user/profile";
    }
}
