package pl.printo3d.onedcutter.cutter1d.cutter.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.CutUnit;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.StockUnit;
import pl.printo3d.onedcutter.cutter1d.services.*;

@ExtendWith(MockitoExtension.class)
public class CutServiceTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private ResultService resultService;

    @Mock
    private ResolveService resolveService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CutService cutService;

    @Test
    void makeOrder_should_save_active_order() {
        ProjectModel testModel = setupProjectModel();

        cutService.calculateProject(testModel);

        verify(projectService, times(1)).saveActiveOrder(testModel);
    }

    ProjectModel setupProjectModel() {
        ProjectModel projectModel = new ProjectModel();

        projectModel.setCutList(new ArrayList<>(Arrays.asList(new CutUnit("220", "5"), new CutUnit("260", "5"))));
        projectModel.setStockList(new ArrayList<>(
                Arrays.asList(new StockUnit("0", "1000", "6", "0"), new StockUnit("1", "1000", "5", "0"))));
        projectModel.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
        projectModel.setProjectName("Default project");
        projectModel.setProjectCreated(LocalDateTime.now());
        projectModel.setProjectModified(LocalDateTime.now());

        return projectModel;
    }

}