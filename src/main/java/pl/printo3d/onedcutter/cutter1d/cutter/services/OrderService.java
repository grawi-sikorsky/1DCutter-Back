package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;

@Service
public class OrderService {

  @Autowired
  private OneDCutService cutService;
  @Autowired
  private ResultService resultService;
  @Autowired
  private UserService userService;

  // Lista zawierajace dlugosci i ilosci surowca
  public List<StockModel> stockList = new ArrayList<StockModel>();

  // Lista zawierajace klucze (dlugosci) i wartosci (ilosc) formatek do ciecia
  public List<CutModel> cutList = new ArrayList<CutModel>();



  public OrderModel returnOrder()
  {
    OrderModel orderList = new OrderModel();
    orderList.cutList.clear();
    orderList.stockList.clear();

    orderList.cutList.add(new CutModel("260", "5"));
    orderList.stockList.add(new StockModel( "1000", "4", "0"));
    
    return orderList;
  }
  
  public OrderModel returnOrder(OrderModel orderModel)
  {    
    return orderModel;
  }



  public ResultModel makeOrder(OrderModel orderModel)
  {
    System.out.println("Make Order in Java");

    orderModel.stockList.forEach(e->System.out.println("ID: " + e.getId() + ", frontID: " + e.getIdFront() + ", Len: " + e.getStockLength() + ", Pcs: " + e.getStockPcs() + ", price: " + e.getStockPrice() + " $" ));
    orderModel.cutList.forEach(e->System.out.println(e.getCutLength() + " " + e.getCutPcs()));

    /** ZAPIS DO BAZY */
    UserModel um;
    um = (UserModel)userService.loadUserByUsername(orderModel.usernameOrder);

    // najpierw czyscimy liste, aby w DB pozbyc sie osieroconych wpisow
    // dlatego getcutlist.addAll! zamiast setCutlist.add!
    um.getOrderModel().getCutList().clear();
    um.getOrderModel().getCutList().addAll(orderModel.cutList);

    um.getOrderModel().getStockList().clear();
    um.getOrderModel().getStockList().addAll(orderModel.stockList);

    orderModel.cutOptions.setId(um.getOrderModel().getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
    um.getOrderModel().setCutOptions(orderModel.cutOptions);

    userService.updateUser(um);
    /** END ZAPIS DO BAZY */

    cutService.firstFit(orderModel);


    return resultService.makeFullResults();
  }

  public ResultModel makeOrderFree(OrderModel orderModel)
  {
    System.out.println("Make FREE Order in Java");

    orderModel.stockList.forEach(e->System.out.println(e.getStockLength() + " " + e.getStockPcs() + " " + e.getStockPrice() + " $" ));
    orderModel.cutList.forEach(e->System.out.println(e.getCutLength() + " " + e.getCutPcs()));

    cutService.cutList = orderModel.cutList;
    cutService.stockList = orderModel.stockList;
    cutService.firstFit(orderModel);
    //this.returnOrder(orderModel); //?

    return resultService.makeFullResults();
  }

}
