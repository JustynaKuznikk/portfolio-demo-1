package projects.portfoliodemo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.portfoliodemo.domain.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
