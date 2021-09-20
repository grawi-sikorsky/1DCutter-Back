package pl.printo3d.onedcutter.cutter1d.cutter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import java.util.logging.Logger;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;

//@ExtendWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    
    @Spy
    private OneDCutService cutService;
    @Spy
    private ResultService resultService;
    @Spy
    private UserService userService;

    OrderService orderServiceTest = spy(OrderService.class);

    private PrintForTest printer;

    @Test
    public void returnOrder_should_return_default_values() {
        OrderModel orderModelTest = new OrderModel();
        orderModelTest = orderServiceTest.returnOrder();

        assertEquals(orderModelTest.getCutList().size(), 1);
        assertEquals(orderModelTest.getStockList().size(), 1);
    }

    @Test
    public void makeOrder_should_return_default_values() {
        OrderModel orderModelTest = new OrderModel();
        
        
        //= orderServiceTest.makeOrder( orderModelTest );



        //assertEquals(orderModelTest.getCutList().size(), 1);
        //assertEquals(orderModelTest.getStockList().size(), 1);
    }

    @Test
    public void costam() {
        System.out.println(orderServiceTest);
    }


}