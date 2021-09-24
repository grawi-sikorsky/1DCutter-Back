package pl.printo3d.onedcutter.cutter1d.userlogin.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutOptions;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserProfileController {

    @Autowired
    private UserService uService;
    @Autowired 
    private OrderService oService;

    // Zapisuje dane uzytkownika z ekranu Profil
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public boolean profileUpdate(@RequestBody UserModel userModel) {
        UserModel uModel = (UserModel) uService.loadUserByUsername(userModel.getUsername());

        uModel.setPhone(userModel.getPhone());
        uModel.setWebsite(userModel.getWebsite());
        uService.updateUser(uModel);

        return true;
    }

    // Load user project
    @RequestMapping(value = "/loadproject", method = RequestMethod.POST)
    public boolean loadProject(@RequestBody UserModel incomingUserModel) {
        
        UserModel uModel = (UserModel) uService.loadUserByUsername(incomingUserModel.getUsername());

        uModel.setActiveOrderId(incomingUserModel.getActiveOrderId());
        uModel.setActiveOrderModel(uModel.getSavedOrderModels().get(incomingUserModel.getActiveOrderId()));

        uService.updateUser(uModel);

        System.out.println("Request /loadproject -> UpdateUser(activeorderID)");

        return true;
    }

    // Save user project
    @RequestMapping(value = "/saveproject", method = RequestMethod.POST)
    public boolean saveProject(@RequestBody UserModel incomingUserModel) {

        UserModel uModel = (UserModel) uService.loadUserByUsername(incomingUserModel.getUsername());
        // zrobic id catch
        if(uModel.getNumberOfSavedItems() <= incomingUserModel.getActiveOrderId() && uModel.getNumberOfSavedItems() < 5 ) {
            OrderModel tmpord = new OrderModel();
            tmpord.setCutList(Arrays.asList(new CutModel("220", "5"), new CutModel("260", "5")));
            tmpord.setStockList( Arrays.asList(new StockModel("0", "1000", "6", "0"), new StockModel("1", "1000", "5", "0")));
            tmpord.setCutOptions(new CutOptions(false, 0d, false));
            tmpord.setProjectName("default name2");
            tmpord.setProjectCreated(LocalDateTime.now());
            tmpord.setProjectModified(LocalDateTime.now());
            uModel.getSavedOrderModels().add(tmpord);
            uModel.setNumberOfSavedItems(uModel.getNumberOfSavedItems()+1);
        }
        uModel.setActiveOrderId(incomingUserModel.getActiveOrderId());
        uService.updateUser(uModel);

        incomingUserModel.getActiveOrderModel().getCutList().forEach(e->e.setId(null));
        incomingUserModel.getActiveOrderModel().getStockList().forEach(e->e.setId(null));

        oService.saveUserOrders(incomingUserModel.getActiveOrderModel()); // tutaj musi trafic zestaw bez id w przeciwnym razie przy kopii ze slota na inny slot bedzie leciec duplicate entry.
        
        System.out.println("Request /saveproject -> UpdateUser(activeorderID)");
        return true;
    }

    /**
     * Testowe
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/getuserprojects", method = RequestMethod.POST)
    public List<OrderModel> getListOfSavedProjects(@RequestParam UserModel userModel) {
        return uService.getListOfSavedProjects(userModel);
    }

}