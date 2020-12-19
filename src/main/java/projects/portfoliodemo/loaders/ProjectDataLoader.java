package projects.portfoliodemo.loaders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import projects.portfoliodemo.domain.model.Project;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.domain.repository.ProjectRepository;
import projects.portfoliodemo.domain.repository.UserRepository;

import java.util.Optional;

@Component
@Profile("heroku")
@Slf4j
@RequiredArgsConstructor
public class ProjectDataLoader implements DataLoader {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public int getOrder() {
        return Integer.MIN_VALUE + 1000;
    }

    @Transactional
    public void loadData() {
        Optional<User> optionalDuke = userRepository.findByUsername("duke@gmail.com");
        if (optionalDuke.isPresent()) {
            User duke = optionalDuke.get();
            Project dukeProject = Project.builder()
                    .name("Duke Loves Java")
                    .description("Duke's hot feelings to Java")
                    .url("http://some.crazy.russian.ru")
                    .user(duke)
                    .build();
            projectRepository.save(dukeProject);
            log.debug("Saved project: {}", dukeProject);
        }
    }
}
