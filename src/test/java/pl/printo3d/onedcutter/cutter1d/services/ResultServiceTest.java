package pl.printo3d.onedcutter.cutter1d.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.mysql.cj.xdevapi.Result;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.printo3d.onedcutter.cutter1d.models.WorkPiece;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.CutUnit;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.StockUnit;
import pl.printo3d.onedcutter.cutter1d.models.results.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultBar;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultModel;
import pl.printo3d.onedcutter.cutter1d.services.ResultService;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @Mock
    private CutService cutService;

    @Mock
    private ResolveService resolveService;

    @InjectMocks
    private ResultService resultService;

    @Test
    void makeFullResults_should_return_return_results(){
        ProjectModel projectModel = setupProject();
        CutterProduct testCutterProduct = setupFirstFitCutterProduct();
        ResultModel testResults = new ResultModel();

        assertEquals(testResults, resultService.makeFullResults(testCutterProduct, projectModel));
    }



    ProjectModel setupProject() {
        ProjectModel projectModel = new ProjectModel();

        projectModel.setCutList(new ArrayList<>(Arrays.asList(
                new CutUnit("200", "2"),
                new CutUnit("300", "2"))));
        projectModel.setStockList(new ArrayList<>(Arrays.asList(
                new StockUnit("0", "1000", "1", "0"),
                new StockUnit("1", "1000", "2", "0"))));
        projectModel.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
        projectModel.setProjectName("Test project");
        projectModel.setProjectCreated(LocalDateTime.now());
        projectModel.setProjectModified(LocalDateTime.now());

        return projectModel;
    }

    CutterProduct setupFirstFitCutterProduct() {
        ProjectModel projectModel = setupProject();
        return resolveService.firstFit(projectModel);
    }

}