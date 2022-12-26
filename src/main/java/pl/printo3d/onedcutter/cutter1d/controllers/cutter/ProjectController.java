package pl.printo3d.onedcutter.cutter1d.controllers.cutter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pl.printo3d.onedcutter.cutter1d.services.UserService;

@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
@CrossOrigin(origins = {"http://localhost:4200","http://10.0.2.2:8080"})
@RestController
@RequestMapping("/user/orders")
public class ProjectController {

    private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService){
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectModel> getAllUserProjects(){
        return projectService.getAllUserProjects();
    }

    @GetMapping("{projectId}")
    public ProjectModel loadProject(@PathVariable Long projectId){
        return projectService.getProject(projectId);
    }

    @PostMapping
    public ProjectModel addNewProject(){
        return projectService.addNewProject();
    }

    @PatchMapping("{orderId}")
    public ProjectModel editProject(@PathVariable Long orderId, @RequestBody ProjectModel incomingProject){
        return projectService.editProject(orderId, incomingProject);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeOrder(@PathVariable String id){
        projectService.removeOrderModel(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}