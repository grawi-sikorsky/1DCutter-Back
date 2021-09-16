package pl.printo3d.onedcutter.cutter1d.cutter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutOptions;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;
import pl.printo3d.onedcutter.cutter1d.cutter.services.ResultService;

@CrossOrigin(origins = "http://localhost:4200")
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
    public OrderModel ProcessOrder(@RequestBody OrderModel orderModel) {
        orderService.makeOrder(orderModel);
        return orderService.returnOrder(orderModel);
    }

    @PostMapping("/cutfree")
    public OrderModel ProcessOrderFree(@RequestBody OrderModel orderModel) {
        orderService.makeOrderFree(orderModel);
        return orderService.returnOrder(orderModel);
    }

    @PostMapping("/setorder")
    public OrderModel setOptions(@RequestBody OrderModel orderModel) {
        orderService.setOrder(orderModel);

        return orderModel;
    }

    @GetMapping("/cut") // not used?
    public OrderModel cut() {
        return orderService.returnOrder();
    }

    @GetMapping("/result")
    public ResultModel result() {
        return resultService.makeFullResults();
    }
}
