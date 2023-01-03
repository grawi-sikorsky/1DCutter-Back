package pl.printo3d.onedcutter.cutter1d.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.CutUnit;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.StockUnit;
import pl.printo3d.onedcutter.cutter1d.models.results.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultModel;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @Mock
    private CutService cutService;

    @Mock
    private JwtUserDetailsService userDetailsService;

    @Spy
    private ResolveService resolveService;

    @InjectMocks
    private ResultService resultService;


    @Test
    void makeFullResults_should_return_return_results(){
        ProjectModel projectModel = setupProject();
        CutterProduct testCutterProduct = resolveService.firstFit(projectModel);
        UserModel testuser = new UserModel();
        testuser.setUsername("testuser");
        testuser.setActiveProjectModel(projectModel);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(testuser);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(testuser);

        ResultModel actualResults = resultService.makeFullResults(testCutterProduct, projectModel);

        assertEquals(actualResults.getResultBars().size(), resultService.makeFullResults(testCutterProduct, projectModel).getResultBars().size());
    }

    @Test
    void makeFullResults_should_return_notFittedBars(){
        ProjectModel testProjectModel = setup_NotFittedToStockProject();
        CutterProduct testCutterProduct = resolveService.firstFit(testProjectModel);
        UserModel testuser = new UserModel();
        testuser.setUsername("testuser");
        testuser.setActiveProjectModel(testProjectModel);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(testuser);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(testuser);

        ResultModel actualResults = resultService.makeFullResults(testCutterProduct, testProjectModel);

        assertNotNull( actualResults.getRemainBars() );
    }



    ProjectModel setupProject() {
        ProjectModel projectModel = new ProjectModel();

        projectModel.setCutList(new ArrayList<>(Arrays.asList(
                new CutUnit("200", "2"),
                new CutUnit("300", "2"))));
        projectModel.setStockList(new ArrayList<>(Arrays.asList(
                new StockUnit("0", "1000", "1", "0"),
                new StockUnit("1", "1000", "2", "0"))));
        projectModel.setCutOptions(
                new CutOptions(false, 0d, false, false, 1000));
        projectModel.setProjectName("Test project");
        projectModel.setProjectCreated(LocalDateTime.now());
        projectModel.setProjectModified(LocalDateTime.now());

        return projectModel;
    }

    ProjectModel setup_NotFittedToStockProject() {
        ProjectModel projectModel = new ProjectModel();

        projectModel.setCutList(new ArrayList<>(Arrays.asList(
                new CutUnit("200", "5"),
                new CutUnit("300", "5"))));
        projectModel.setStockList(new ArrayList<>(Arrays.asList(
                new StockUnit("0", "1000", "1", "10"),
                new StockUnit("1", "1000", "1", "20"))));
        projectModel.setCutOptions(
                new CutOptions(false, 4d, false, false, 1000));
        projectModel.setProjectName("Test project");
        projectModel.setProjectCreated(LocalDateTime.now());
        projectModel.setProjectModified(LocalDateTime.now());

        return projectModel;
    }
}