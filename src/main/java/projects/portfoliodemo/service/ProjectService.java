package projects.portfoliodemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.portfoliodemo.converter.ProjectConverter;
import projects.portfoliodemo.domain.model.Project;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.domain.repository.ProjectRepository;
import projects.portfoliodemo.domain.repository.UserRepository;
import projects.portfoliodemo.web.command.CreateProjectCommand;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectConverter projectConverter;

    @Transactional
    public void add(CreateProjectCommand createProjectCommand) {
        log.debug("Dane do utworzenia projektu: {}", createProjectCommand);

        Project project = projectConverter.from(createProjectCommand);
        updateProjectWithUser(project);
        log.debug("Projekt do zapisu: {}", project);

        projectRepository.save(project);
        log.debug("Zapisany projekt: {}", project);
    }

    private void updateProjectWithUser(Project project) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getAuthenticatedUser(username);
        project.setUser(user);
    }
}
