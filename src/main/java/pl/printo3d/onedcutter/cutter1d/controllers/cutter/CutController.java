package pl.printo3d.onedcutter.cutter1d.controllers.cutter;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.ResultModel;
import pl.printo3d.onedcutter.cutter1d.services.CutService;
import pl.printo3d.onedcutter.cutter1d.services.ProjectService;

@CrossOrigin(origins = {"http://localhost:4200","http://10.0.2.2:8080"})
@RestController
public class CutController {

    private ProjectService projectService;
    private CutService cutService;

    public CutController(CutService cutService, ProjectService projectService){
        this.cutService = cutService;
        this.projectService = projectService;
    }

    // OBLICZ LOGGED
    @PostMapping("/cut")
    public ResultModel resolveCutting(@RequestBody ProjectModel projectModel) {
        return cutService.makeOrder(projectModel);
    }

    // Oblicz nie Logged
    @PostMapping("/cutfree")
    public ResultModel processOrderFree(@RequestBody ProjectModel orderModel) {
        return cutService.makeOrderFree(orderModel);
    }

    // Zapisuje bierzacy orderModel do bazy (debounced save na froncie)
    @PostMapping("/setorder")
    public ProjectModel setOptions(@RequestBody ProjectModel orderModel) {
        projectService.saveActiveOrder(orderModel);

        return orderModel;
    }
}
