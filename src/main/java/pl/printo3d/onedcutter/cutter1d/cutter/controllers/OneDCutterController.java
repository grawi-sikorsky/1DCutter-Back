package pl.printo3d.onedcutter.cutter1d.cutter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;
import pl.printo3d.onedcutter.cutter1d.cutter.services.ResultService;

@CrossOrigin(origins = "https://onedcutterfront.herokuapp.com")
@RestController
public class OneDCutterController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private OrderService orderService;

    /**
     * Showing home page of One D Cutter, does nothing but return order details (cutlist, stocklist) - default or saved in user database.
     *
     * @return OrderModel
     */
    @GetMapping("/1dcut") // not used?
    public OrderModel showCuts() {
        return orderService.returnOrder();
    }

    // OBLICZ LOGGED
    @PostMapping("/cut")
    public OrderModel ProcessOrder(@RequestBody OrderModel orderModel, @RequestHeader HttpHeaders head) {
        System.out.println(head);
        orderService.makeOrder(orderModel);
        return orderService.returnOrder(orderModel);
    }

    // Oblicz nie Logged
    @PostMapping("/cutfree")
    public OrderModel ProcessOrderFree(@RequestBody OrderModel orderModel,@RequestHeader HttpHeaders head) {
        System.out.println(head);
        orderService.makeOrderFree(orderModel);
        return orderService.returnOrder(orderModel);
    }

    // Zapisuje bierzacy orderModel do bazy
    @PostMapping("/setorder")
    public OrderModel setOptions(@RequestBody OrderModel orderModel) {
        orderService.saveActiveOrder(orderModel);

        return orderModel;
    }

    // Zwraca wyniki
    @GetMapping("/result")
    public ResultModel result() {
        return resultService.makeFullResults();
    }
}
