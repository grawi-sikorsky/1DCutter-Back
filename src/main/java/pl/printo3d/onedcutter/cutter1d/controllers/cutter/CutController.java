package pl.printo3d.onedcutter.cutter1d.controllers.cutter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultModel;
import pl.printo3d.onedcutter.cutter1d.services.CutService;

@CrossOrigin(origins = {"http://localhost:4200","http://10.0.2.2:8080"})
@RestController
public class CutController {

    private CutService cutService;

    public CutController(CutService cutService){
        this.cutService = cutService;
    }

    // OBLICZ LOGGED
    @PostMapping("/cut")
    public ResponseEntity<ResultModel> resolveCutting(@RequestBody ProjectModel projectModel) {
        return ResponseEntity.status(HttpStatus.OK).body(cutService.calculateProject(projectModel));
    }

    // Oblicz nie Logged
    @PostMapping("/cutfree")
    public ResponseEntity<ResultModel> processOrderFree(@RequestBody ProjectModel projectModel) {
        return ResponseEntity.status(HttpStatus.OK).body( cutService.calculateProjectFree(projectModel) );
    }
}
