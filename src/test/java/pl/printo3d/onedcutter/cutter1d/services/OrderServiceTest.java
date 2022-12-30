package pl.printo3d.onedcutter.cutter1d.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.results.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultModel;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.services.ResolveService;
import pl.printo3d.onedcutter.cutter1d.services.CutService;
import pl.printo3d.onedcutter.cutter1d.services.ProjectService;
import pl.printo3d.onedcutter.cutter1d.services.ResultService;
import pl.printo3d.onedcutter.cutter1d.services.UserService;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final String USERNAME = "username";
    private static final UserModel PRINCIPAL = new UserModel(
            "username",
            "password"
    );
    @InjectMocks
    CutService cutService;

    @Mock
    private ResolveService resolveService;
    @Mock
    private ResultService resultService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(
                new SecurityContextImpl(
                        new TestingAuthenticationToken(
                                PRINCIPAL,
                                "credentials"
                        )
                )
        );
        ProjectModel activeProjectModel = new ProjectModel();
        activeProjectModel.setCutOptions(new CutOptions());
        PRINCIPAL.setActiveProjectModel(activeProjectModel);
    }

    @Test
    void returnOrder_should_return_default_values() {
        // //when
        // OrderModel orderModelTest = orderServiceTest.returnOrder();

        // //then
        // assertEquals(orderModelTest.getCutList().size(), 1);
        // CutModel cutModel = orderModelTest.getCutList().get(0);
        // assertEquals("260", cutModel.getCutLength());
        // assertEquals("5", cutModel.getCutPcs());

        // assertEquals(orderModelTest.getStockList().size(), 1);
        // StockModel stockModel = orderModelTest.getStockList().get(0);
        // assertEquals("0", stockModel.getIdFront());
        // assertEquals("1000", stockModel.getStockLength());
        // assertEquals("4", stockModel.getStockPcs());
        // assertEquals("0", stockModel.getStockPrice());

        // verifyNoInteractions(cutService, resultService, userService);
    }

    @Test
    void makeOrder_should_return_default_values() {
        //given
        ProjectModel orderModelTest = new ProjectModel();
        orderModelTest.setCutOptions(new CutOptions());

        CutterProduct cutterProduct = new CutterProduct();
        when(resolveService.firstFit(orderModelTest)).thenReturn(cutterProduct);

        ResultModel resultModel = new ResultModel();
        when(resultService.makeFullResults(cutterProduct, orderModelTest)).thenReturn(resultModel);

        when(userService.loadUserByUsername(USERNAME))
                .thenReturn(PRINCIPAL);

        //when
        ResultModel model = cutService.calculateProject(orderModelTest);


        //then
        assertEquals(resultModel, model);
        verify(resolveService).firstFit(orderModelTest);
        verify(resultService).makeFullResults(cutterProduct, orderModelTest);
        verify(userService).loadUserByUsername(USERNAME);
    }

    @Test
    void makeFreeOrder_should_return_default_vals()
    {
        // given
        ProjectModel testOrder = new ProjectModel();

        CutterProduct cProduct = new CutterProduct();
        when(resolveService.firstFit(testOrder)).thenReturn(cProduct);

        ResultModel rModel = new ResultModel();
        when(resultService.makeFullResults(cProduct, testOrder)).thenReturn(rModel);

        // when
        ResultModel testResult = cutService.calculateProjectFree(testOrder);

        // them
        assertEquals(testResult, rModel);
        verify(resolveService).firstFit(testOrder);
        verify(resultService).makeFullResults(cProduct, testOrder);
    }

    @Test
    void saveActiveOrder_should_niewiemco() // toć void, wiec coz ma zwrocic? @_@
            {
                // given
                ProjectModel testOrder = new ProjectModel();

                // when
                //cutService.saveActiveOrder(testOrder);

                // then
            }

    @Test
    void costam() {
        System.out.println(cutService);
    }


}