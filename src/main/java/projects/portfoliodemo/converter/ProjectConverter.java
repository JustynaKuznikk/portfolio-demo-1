package projects.portfoliodemo.converter;

import org.springframework.stereotype.Component;
import projects.portfoliodemo.domain.model.Project;
import projects.portfoliodemo.web.command.CreateProjectCommand;

@Component
public class ProjectConverter {

    public Project from(CreateProjectCommand command) {
        return Project.builder()
                .name(command.getName())
                .url(command.getUrl())
                .description(command.getDescription())
                .build();
    }
}
