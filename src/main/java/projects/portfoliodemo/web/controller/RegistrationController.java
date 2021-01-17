package projects.portfoliodemo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import projects.portfoliodemo.service.UserService;

@Controller @RequestMapping("/register")
@Slf4j @RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
}
