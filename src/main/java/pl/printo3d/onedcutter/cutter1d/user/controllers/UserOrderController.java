package pl.printo3d.onedcutter.cutter1d.user.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutOptions;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;
import pl.printo3d.onedcutter.cutter1d.user.models.UserDTO;
import pl.printo3d.onedcutter.cutter1d.user.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.user.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    private final static Logger logger = LoggerFactory.getLogger(UserOrderController.class);
    private final UserService userService;
    private final OrderService orderService;

    public UserOrderController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    // for now 0-4 list index not db index!
    @GetMapping("{orderId}")
    public OrderModel loadOrder(@PathVariable Integer orderId){
        UserModel userModel = (UserModel) userService.loadUserByUsername( ((UserModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        return userModel.getSavedOrderModels().get(orderId);
    }

    @PostMapping
    public OrderModel saveOrder(@RequestBody OrderModel incomingOrderModel){
        UserModel userModel = (UserModel) userService.loadUserByUsername( ((UserModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        userModel.setActiveOrderModel(incomingOrderModel);
        return userModel.getActiveOrderModel();
    }

    @PatchMapping("{activeOrderId}")
    public UserDTO setActiveOrder(@PathVariable Integer activeOrderId){
        UserModel userModel = (UserModel) userService.loadUserByUsername( ((UserModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        userModel.setActiveOrderId(activeOrderId);
        return new UserDTO(userModel);
    }

    // Load user project
    @RequestMapping(value = "/loadproject", method = RequestMethod.POST)
    public boolean loadProject(@RequestBody UserModel incomingUserModel) {
        
        UserModel uModel = (UserModel) userService.loadUserByUsername(incomingUserModel.getUsername());

        uModel.setActiveOrderId(incomingUserModel.getActiveOrderId());
        uModel.setActiveOrderModel(uModel.getSavedOrderModels().get(incomingUserModel.getActiveOrderId()));

        userService.updateUser(uModel);

        logger.info("Request /loadproject -> UpdateUser(activeorderID)");

        return true;
    }

    // Save user project
    @RequestMapping(value = "/saveproject", method = RequestMethod.POST)
    public boolean saveProject(@RequestBody UserModel incomingUserModel) {

        UserModel uModel = (UserModel) userService.loadUserByUsername(incomingUserModel.getUsername());
        // zrobic id catch
        if(uModel.getNumberOfSavedItems() <= incomingUserModel.getActiveOrderId() && uModel.getNumberOfSavedItems() < 5 ) {
            OrderModel tmpord = new OrderModel();
            tmpord.setCutList(Arrays.asList(new CutModel("220", "5"), new CutModel("260", "5")));
            tmpord.setStockList( Arrays.asList(new StockModel("0", "1000", "6", "0"), new StockModel("1", "1000", "5", "0")));
            tmpord.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
            tmpord.setProjectName("default name2");
            tmpord.setProjectCreated(LocalDateTime.now());
            tmpord.setProjectModified(LocalDateTime.now());
            uModel.getSavedOrderModels().add(tmpord);
            uModel.setNumberOfSavedItems(uModel.getNumberOfSavedItems()+1);
        }
        uModel.setActiveOrderId(incomingUserModel.getActiveOrderId());
        userService.updateUser(uModel);

        incomingUserModel.getActiveOrderModel().getCutList().forEach(e->e.setId(null));
        incomingUserModel.getActiveOrderModel().getStockList().forEach(e->e.setId(null));

        orderService.saveUserOrders(incomingUserModel.getActiveOrderModel()); // tutaj musi trafic zestaw bez id w przeciwnym razie przy kopii ze slota na inny slot bedzie leciec duplicate entry.
        
        logger.info("Request /saveproject -> UpdateUser(activeorderID)");
        return true;
    }

    /**
     * Testowe
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/getuserprojects", method = RequestMethod.POST)
    public List<OrderModel> getListOfSavedProjects(@RequestParam UserModel userModel) {
        return userService.getListOfSavedProjects(userModel);
    }

}