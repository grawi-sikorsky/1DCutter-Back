package pl.printo3d.onedcutter.cutter1d.repo;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;

public interface ProjectRepository extends CrudRepository<ProjectModel, Long>{

    ProjectModel findProjectModelById(Long id);
    ProjectModel getById(Long id);
    ProjectModel findProjectModelByIdAndUserId(Long id, Long userId);

    @Transactional
    void deleteProjectModelById(Long id);
}
