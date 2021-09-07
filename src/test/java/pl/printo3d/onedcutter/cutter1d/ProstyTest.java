package pl.printo3d.onedcutter.cutter1d;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutOptions;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;

class ProstyTest {

  @Test
  public void testujemycostam()
  {
    //given
    OrderService os = new OrderService();
    
    //when
    OrderModel om = os.returnOrder();

    //then
    OrderModel tstorder = new OrderModel();
    tstorder.cutList.add(new CutModel("260", "5"));
    tstorder.stockList.add(new StockModel("1000", "4", "0"));
    

    assertEquals(om, tstorder);
  }

}
