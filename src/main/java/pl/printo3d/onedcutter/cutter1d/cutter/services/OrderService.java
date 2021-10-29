package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.user.models.UserDTO;
import pl.printo3d.onedcutter.cutter1d.user.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.user.repo.OrderRepository;
import pl.printo3d.onedcutter.cutter1d.user.services.UserService;

@Service
public class OrderService {

    private final OneDCutService cutService;
    private final ResultService resultService;
    private final UserService userService;
    private final OrderRepository orderRepository;

    public OrderService(OneDCutService cutService,ResultService resultService,UserService userService, OrderRepository orderRepository){
        this.cutService = cutService;
        this.resultService = resultService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    /**
     * Wykonuje obliczenia dla zalogowanego Usera -> TODO: roznica jest tylko w zapisie do bazy -> scalić w jedno.
     * @param orderModel
     * @return ResultModel
     */
    public ResultModel makeOrder(OrderModel orderModel) {

        /** ZAPIS DO BAZY [ACTIVE ORDER] */
        this.saveActiveOrder(orderModel);
        /** END ZAPIS DO BAZY [ACTIVE ORDER] */
        
        if(orderModel.getCutOptions().isOptionAlgo()){
            return resultService.makeFullResults( this.cutService.newAlgo(cutService.firstFit(orderModel), orderModel), orderModel );
        }
        else{
            return resultService.makeFullResults( cutService.firstFit(orderModel), orderModel );
        }
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

        userService.updateUser(new UserDTO(um));
        /** END ZAPIS DO BAZY */
    }

    /**
     * Zapisuje do bazy wyłącznie jeden bierzący order - nie zapisuje ich do slotów pamieci usera
     * @param incomingOrderModel
     */
    public OrderModel saveActiveOrder(OrderModel incomingOrderModel) {
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

        userService.updateUser(new UserDTO(um));
        /** END ZAPIS DO BAZY */
        return um.getActiveOrderModel();
    }


    public OrderModel addOrderModel(OrderModel incomingOrderModel){
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        if(userModel.getNumberOfSavedItems() < 5){
            userModel.getSavedOrderModels().add(incomingOrderModel);
            userModel.setActiveOrderModel(incomingOrderModel);
            userModel.setNumberOfSavedItems(userModel.getSavedOrderModels().size());

            userService.saveUserEntity(userModel);

            return userModel.getActiveOrderModel();
        }
        else throw new RuntimeException("There's no more space for this user");
    }

    public OrderModel editOrderModel(Long id, OrderModel incomingOrderModel) {
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        if( orderRepository.getByIdAndUserId(id, userModel.getId()) != null ){
            OrderModel orderModel = orderRepository.getByIdAndUserId(id, userModel.getId());
            orderModel.getCutList().clear();
            orderModel.getCutList().addAll(incomingOrderModel.getCutList());

            orderModel.getStockList().clear();
            orderModel.getStockList().addAll(incomingOrderModel.getStockList());

            orderModel.setCutOptions(incomingOrderModel.getCutOptions());

            orderModel.setProjectName(incomingOrderModel.getProjectName());
            orderModel.setProjectModified(LocalDateTime.now());

            orderRepository.save(orderModel);

            return orderModel;
        }
        else throw new RuntimeException("User or model not found!");
    }

    public void removeOrderModel(Long id){
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        if(orderRepository.findByIdAndUserId(id, userModel.getId()) != null){
            orderRepository.deleteById(id);
            
            userModel.setNumberOfSavedItems(userModel.getSavedOrderModels().size());
            userService.saveUserEntity(userModel);
        } else throw new RuntimeException("No user or ordermodel");
        
    }
}
