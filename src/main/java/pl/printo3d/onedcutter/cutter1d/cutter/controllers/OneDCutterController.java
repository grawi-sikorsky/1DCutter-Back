package pl.printo3d.onedcutter.cutter1d.cutter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OneDCutterController {

    @Autowired
    private OrderService orderService;

    /**
     * Showing home page of One D Cutter, does nothing but return order details (cutlist, stocklist) - default or saved in user database.
     *
     * @return OrderModel
     */

    // OBLICZ LOGGED
    @PostMapping("/cut")
    public ResultModel ProcessOrder(@RequestBody OrderModel orderModel) {
        return orderService.makeOrder(orderModel);
    }

    // Oblicz nie Logged
    @PostMapping("/cutfree")
    public ResultModel ProcessOrderFree(@RequestBody OrderModel orderModel) {
        return orderService.makeOrderFree(orderModel);
    }

    // Zapisuje bierzacy orderModel do bazy (debounced save na froncie)
    @PostMapping("/setorder")
    public OrderModel setOptions(@RequestBody OrderModel orderModel) {
        orderService.saveActiveOrder(orderModel);

        return orderModel;
    }
}
