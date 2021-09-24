package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    // Default
    public OrderModel returnOrder() {
        OrderModel orderList = new OrderModel();

        orderList.getCutList().add(new CutModel("260", "5"));
        orderList.getStockList().add(new StockModel("0", "1000", "4", "0"));

        return orderList;
    }

    // A ODDEJ PAN COS POWZIĄŁ!! :D
    public OrderModel returnOrder(OrderModel orderModel) {
        return orderModel;
    }

    /**
     * Wykonuje obliczenia dla zalogowanego Usera -> TODO: roznica jest tylko w zapisie do bazy -> scalić w jedno.
     * @param orderModel
     * @return ResultModel
     */
    public ResultModel makeOrder(OrderModel orderModel) {

        System.out.println("Make Order:");
        orderModel.getStockList().forEach(e -> System.out.println("ID: " + e.getId() + ", frontID: " + e.getIdFront() + ", Len: " + e.getStockLength() + ", Pcs: " + e.getStockPcs() + ", price: " + e.getStockPrice() + " $"));
        orderModel.getCutList().forEach(e -> System.out.println(e.getCutLength() + " " + e.getCutPcs()));

        /** ZAPIS DO BAZY [ACTIVE ORDER] */
        this.saveActiveOrder(orderModel);
        /** END ZAPIS DO BAZY [ACTIVE ORDER] */

        return resultService.makeFullResults( cutService.firstFit(orderModel), orderModel );
    }

    /**
     * Wykonuje obliczenia dla usera niezalogowanego
     * @param orderModel
     * @return ResultModel
     */
    public ResultModel makeOrderFree(OrderModel orderModel) {

        System.out.println("Make FREE Order:");
        orderModel.getStockList().forEach(e -> System.out.println("ID: " + e.getId() + ", frontID: " + e.getIdFront() + ", Len: " + e.getStockLength() + ", Pcs: " + e.getStockPcs() + ", price: " + e.getStockPrice() + " $"));
        orderModel.getCutList().forEach(e -> System.out.println(e.getCutLength() + " " + e.getCutPcs()));

        return resultService.makeFullResults( cutService.firstFit(orderModel), orderModel );
    }

    /**
     * Zapisuje do bazy Order, który ma trafic w "pamiec stala" tj. wolne sloty kazdego usera
     * @param incomingOrderModel
     */
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

    /**
     * Zapisuje do bazy wyłącznie jeden bierzący order - nie zapisuje ich do slotów pamieci usera
     * @param incomingOrderModel
     */
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
