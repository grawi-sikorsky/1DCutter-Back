package pl.printo3d.onedcutter.cutter1d.services;

import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.results.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultModel;

@Service
public class CutService {

    private final ResultService resultService;
    private final ProjectService projectService;
    private final ResolveService resolveService;

    public CutService(ResultService resultService, ProjectService projectService, ResolveService resolveService) {
        this.resultService = resultService;
        this.projectService = projectService;
        this.resolveService = resolveService;
    }

    /**
     * Wykonuje obliczenia dla zalogowanego Usera
     * 
     * @param projectModel
     * @return ResultModel
     */
    public ResultModel calculateProject(ProjectModel projectModel) {
        projectService.saveActiveProject(projectModel);
        CutterProduct cutterProduct = resolveService.firstFit(projectModel);
        if( projectModel.getCutOptions().isOptionAlgo() ){
            cutterProduct = resolveService.newAlgo(cutterProduct, projectModel);
        }
        return resultService.makeFullResults(cutterProduct, projectModel);
    }

    /**
     * Wykonuje obliczenia dla usera niezalogowanego
     * 
     * @param projectModel
     * @return ResultModel
     */
    public ResultModel calculateProjectFree(ProjectModel projectModel) {
        return resultService.makeFullResults(resolveService.firstFit(projectModel), projectModel);
    }
}