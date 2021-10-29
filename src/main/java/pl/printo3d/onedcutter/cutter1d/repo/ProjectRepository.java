package pl.printo3d.onedcutter.cutter1d.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectModel, Long>{

    ProjectModel findProjectModelById(Long id);
    ProjectModel getById(Long id);
    ProjectModel getByIdAndUserId(Long id, Long userId);
    ProjectModel findByIdAndUserId(Long id, Long userId);
    ProjectModel findProjectModelByIdAndUserId(Long id, Long userId);
}
