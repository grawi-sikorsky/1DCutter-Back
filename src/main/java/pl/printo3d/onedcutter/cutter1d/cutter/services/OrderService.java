package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.Local;
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

    public OrderModel orderList = new OrderModel();

    // Lista zawierajace dlugosci i ilosci surowca
    public List<StockModel> stockList = new ArrayList<StockModel>();

    // Lista zawierajace klucze (dlugosci) i wartosci (ilosc) formatek do ciecia
    public List<CutModel> cutList = new ArrayList<CutModel>();

    public OrderModel returnOrder() {
        orderList.getCutList().clear();
        orderList.getStockList().clear();

        orderList.getCutList().add(new CutModel("260", "5"));
        orderList.getStockList().add(new StockModel("0", "1000", "4", "0"));

        return orderList;
    }

    public OrderModel returnOrder(OrderModel orderModel) {
        return orderModel;
    }

    public ResultModel makeOrder(OrderModel orderModel) {
        System.out.println("Make Order in Java");

        orderModel.getStockList().forEach(e -> System.out.println("ID: " + e.getId() + ", frontID: " + e.getIdFront() + ", Len: " + e.getStockLength() + ", Pcs: " + e.getStockPcs() + ", price: " + e.getStockPrice() + " $"));
        orderModel.getCutList().forEach(e -> System.out.println(e.getCutLength() + " " + e.getCutPcs()));

        /** ZAPIS DO BAZY [ACTIVE ORDER] */
        this.saveActiveOrder(orderModel);
        /** END ZAPIS DO BAZY [ACTIVE ORDER] */

        orderList.clearOrder();

        cutService.cutList = orderModel.getCutList();
        cutService.stockList = orderModel.getStockList();
        cutService.firstFit(orderModel);

        return resultService.makeFullResults();
    }

    public ResultModel makeOrderFree(OrderModel orderModel) {
        System.out.println("Make FREE Order in Java");

        orderModel.getStockList().forEach(e -> System.out.println("ID: " + e.getId() + ", frontID: " + e.getIdFront() + ", Len: " + e.getStockLength() + ", Pcs: " + e.getStockPcs() + ", price: " + e.getStockPrice() + " $"));
        orderModel.getCutList().forEach(e -> System.out.println(e.getCutLength() + " " + e.getCutPcs()));

        orderList.clearOrder();
        cutService.cutList = orderModel.getCutList();
        cutService.stockList = orderModel.getStockList();

        cutService.firstFit(orderModel);
        // this.returnOrder(orderModel); //?

        return resultService.makeFullResults();
    }

    public void saveUserOrders(OrderModel incomingOrderModel) {
        /** ZAPIS DO BAZY */
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel um;
        um = (UserModel) userService.loadUserByUsername(ud.getUsername());

        // najpierw czyscimy liste, aby w DB pozbyc sie osieroconych wpisow
        // dlatego getcutlist.addAll! zamiast setCutlist.add!
        um.getSavedOrderModels().get(um.getActiveOrderId()).getCutList().clear();
        um.getSavedOrderModels().get(um.getActiveOrderId()).getCutList().addAll(incomingOrderModel.getCutList());

        um.getSavedOrderModels().get(um.getActiveOrderId()).getStockList().clear();
        um.getSavedOrderModels().get(um.getActiveOrderId()).getStockList().addAll(incomingOrderModel.getStockList());

        incomingOrderModel.getCutOptions().setId(um.getSavedOrderModels().get(um.getActiveOrderId()).getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        um.getSavedOrderModels().get(um.getActiveOrderId()).setCutOptions(incomingOrderModel.getCutOptions());

        um.getSavedOrderModels().get(um.getActiveOrderId()).setProjectName(incomingOrderModel.getProjectName());
        um.getSavedOrderModels().get(um.getActiveOrderId()).setProjectModified(LocalDateTime.now());

        userService.updateUser(um);
        /** END ZAPIS DO BAZY */
    }

    public void saveActiveOrder(OrderModel incomingOrderModel) {
        /** ZAPIS DO BAZY */
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel um;
        um = (UserModel) userService.loadUserByUsername(ud.getUsername());

        // najpierw czyscimy liste, aby w DB pozbyc sie osieroconych wpisow
        // dlatego getcutlist.addAll! zamiast setCutlist.add!
        um.getActiveOrderModel().getCutList().clear();
        um.getActiveOrderModel().getCutList().addAll(incomingOrderModel.getCutList());

        um.getActiveOrderModel().getStockList().clear();
        um.getActiveOrderModel().getStockList().addAll(incomingOrderModel.getStockList());

        incomingOrderModel.getCutOptions().setId(um.getActiveOrderModel().getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        um.getActiveOrderModel().setCutOptions(incomingOrderModel.getCutOptions());

        um.getActiveOrderModel().setProjectName(incomingOrderModel.getProjectName());
        um.getActiveOrderModel().setProjectModified(LocalDateTime.now());

        userService.updateUser(um);
        /** END ZAPIS DO BAZY */
    }

}
