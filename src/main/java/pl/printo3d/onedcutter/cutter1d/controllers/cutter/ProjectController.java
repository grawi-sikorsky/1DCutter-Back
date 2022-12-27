package pl.printo3d.onedcutter.cutter1d.controllers.cutter;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.services.ProjectService;

@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:8080" })
@RestController
@RequestMapping("/user/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectModel>> getAllUserProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllUserProjects());
    }

    @GetMapping("{projectId}")
    public ResponseEntity<ProjectModel> loadProject(@PathVariable Long projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProject(projectId));
    }

    @PostMapping
    public ResponseEntity<ProjectModel> addNewProject() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.addNewProject());
    }

    @PatchMapping("{projectId}")
    public ResponseEntity<ProjectModel> editProject(@PathVariable Long projectId, @RequestBody ProjectModel incomingProject) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.editProject(projectId, incomingProject));
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<Void> removeOrder(@PathVariable Long projectId) {
        projectService.removeOrderModel(projectId);
        return ResponseEntity.noContent().build();
    }
}