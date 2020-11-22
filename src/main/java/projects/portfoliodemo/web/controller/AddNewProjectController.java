package projects.portfoliodemo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projects.portfoliodemo.web.command.CreateProjectCommand;

@Controller
@RequestMapping("/projects")
@Slf4j
@RequiredArgsConstructor
public class AddNewProjectController {

    @GetMapping
    public String getAddProjectPage(Model model) {
        model.addAttribute(new CreateProjectCommand());
        return "projects/add";
    }
}
