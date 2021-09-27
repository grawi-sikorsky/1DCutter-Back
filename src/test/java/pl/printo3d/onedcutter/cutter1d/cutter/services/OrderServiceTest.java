package pl.printo3d.onedcutter.cutter1d.cutter.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutOptions;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private static final String USERNAME = "username";
    private static final UserModel PRINCIPAL = new UserModel(
            "username",
            "password"
    );
    private static final String PROJECT_NAME = "test_project";
    private static final Long CUT_OPTIONS_ID = 1L;

    @InjectMocks
    OrderService orderServiceTest;

    @Mock
    private OneDCutService cutService;
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
        OrderModel activeOrderModel = new OrderModel();
        activeOrderModel.setCutOptions(new CutOptions());
        PRINCIPAL.setActiveOrderModel(activeOrderModel);
    }

    @Test
    public void returnOrder_should_return_default_values() {
        //when
        OrderModel orderModelTest = orderServiceTest.returnOrder();

        //then
        assertEquals(orderModelTest.getCutList().size(), 1);
        CutModel cutModel = orderModelTest.getCutList().get(0);
        assertEquals("260", cutModel.getCutLength());
        assertEquals("5", cutModel.getCutPcs());

        assertEquals(orderModelTest.getStockList().size(), 1);
        StockModel stockModel = orderModelTest.getStockList().get(0);
        assertEquals("0", stockModel.getIdFront());
        assertEquals("1000", stockModel.getStockLength());
        assertEquals("4", stockModel.getStockPcs());
        assertEquals("0", stockModel.getStockPrice());

        verifyNoInteractions(cutService, resultService, userService);
    }

    @Test
    public void makeOrder_should_return_default_values() {
        //given
        OrderModel orderModelTest = new OrderModel();
        orderModelTest.setCutOptions(new CutOptions());

        CutterProduct cutterProduct = new CutterProduct();
        when(cutService.firstFit(orderModelTest)).thenReturn(cutterProduct);

        ResultModel resultModel = new ResultModel();
        when(resultService.makeFullResults(cutterProduct, orderModelTest)).thenReturn(resultModel);

        when(userService.loadUserByUsername(USERNAME))
                .thenReturn(PRINCIPAL);

        //when
        ResultModel model = orderServiceTest.makeOrder(orderModelTest);


        //then
        assertEquals(resultModel, model);
        verify(cutService).firstFit(orderModelTest);
        verify(resultService).makeFullResults(cutterProduct, orderModelTest);
        verify(userService).loadUserByUsername(USERNAME);
    }

    @Test 
    public void makeFreeOrder_should_return_default_vals()
    {
        // given
        OrderModel testOrder = new OrderModel();

        CutterProduct cProduct = new CutterProduct();
        when(cutService.firstFit(testOrder)).thenReturn(cProduct);

        ResultModel rModel = new ResultModel();
        when(resultService.makeFullResults( cProduct , testOrder )).thenReturn(rModel);

        // when
        ResultModel testResult = orderServiceTest.makeOrderFree(testOrder);

        // them
        assertEquals(testResult, rModel);
        verify(cutService).firstFit(testOrder);
        verify(resultService).makeFullResults(cProduct, testOrder);
    }

    @Test 
    public void saveActiveOrder_should_niewiemco() // toć void, wiec coz ma zwrocic? @_@
    {
        // given
        UserModel principal = new UserModel(
                "username",
                "password"
        );
        OrderModel activeOrderModel = new OrderModel();
        CutOptions cutOptions = new CutOptions();
        cutOptions.setId(CUT_OPTIONS_ID);
        activeOrderModel.setCutOptions(cutOptions);
        principal.setActiveOrderModel(activeOrderModel);
        when(userService.loadUserByUsername(USERNAME))
                .thenReturn(principal);

        OrderModel testOrder = new OrderModel();
        testOrder.setCutOptions(new CutOptions());
        List<CutModel> cutList = Collections.singletonList(new CutModel());
        testOrder.setCutList(cutList);
        List<StockModel> stockList = Collections.singletonList(new StockModel());
        testOrder.setStockList(stockList);
        testOrder.setProjectName(PROJECT_NAME);

        // when
        orderServiceTest.saveActiveOrder(testOrder);

        // then
        assertEquals(cutList, principal.getActiveOrderModel().getCutList());
        assertEquals(stockList, principal.getActiveOrderModel().getStockList());
        assertEquals(CUT_OPTIONS_ID, principal.getActiveOrderModel().getCutOptions().getId());
        assertEquals(PROJECT_NAME, principal.getActiveOrderModel().getProjectName());
        verify(userService).updateUser(principal);
    }

    @Test
    public void costam() {
        System.out.println(orderServiceTest);
    }


}