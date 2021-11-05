package pl.printo3d.onedcutter.cutter1d.services;

import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.ResultModel;

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
     * Wykonuje obliczenia dla zalogowanego Usera -> TODO: roznica jest tylko w
     * zapisie do bazy -> scalić w jedno.
     * 
     * @param orderModel
     * @return ResultModel
     */
    public ResultModel makeOrder(ProjectModel orderModel) {

        /** ZAPIS DO BAZY [ACTIVE ORDER] */
        projectService.saveActiveOrder(orderModel);
        /** END ZAPIS DO BAZY [ACTIVE ORDER] */

        if (orderModel.getCutOptions().isOptionAlgo()) {
            return resultService.makeFullResults(this.resolveService.newAlgo(resolveService.firstFit(orderModel), orderModel), orderModel);
        } else {
            return resultService.makeFullResults(resolveService.firstFit(orderModel), orderModel);
        }
    }

    /**
     * Wykonuje obliczenia dla usera niezalogowanego
     * 
     * @param orderModel
     * @return ResultModel
     */
    public ResultModel makeOrderFree(ProjectModel orderModel) {

        System.out.println("Make FREE Order:");
        orderModel.getStockList()
                .forEach(e -> System.out.println("ID: " + e.getId() + ", frontID: " + e.getIdFront() + ", Len: "
                        + e.getStockLength() + ", Pcs: " + e.getStockPcs() + ", price: " + e.getStockPrice() + " $"));
        orderModel.getCutList().forEach(e -> System.out.println(e.getCutLength() + " " + e.getCutPcs()));

        return resultService.makeFullResults(resolveService.firstFit(orderModel), orderModel);
    }

}
