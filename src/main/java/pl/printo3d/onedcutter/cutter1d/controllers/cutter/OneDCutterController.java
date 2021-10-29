package pl.printo3d.onedcutter.cutter1d.controllers.cutter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.ResultModel;
import pl.printo3d.onedcutter.cutter1d.services.ProjectService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OneDCutterController {

    @Autowired
    private ProjectService orderService;

    /**
     * Showing home page of One D Cutter, does nothing but return order details (cutlist, stocklist) - default or saved in user database.
     *
     * @return OrderModel
     */

    // OBLICZ LOGGED
    @PostMapping("/cut")
    public ResultModel ProcessOrder(@RequestBody ProjectModel orderModel) {
        return orderService.makeOrder(orderModel);
    }

    // Oblicz nie Logged
    @PostMapping("/cutfree")
    public ResultModel ProcessOrderFree(@RequestBody ProjectModel orderModel) {
        return orderService.makeOrderFree(orderModel);
    }

    // Zapisuje bierzacy orderModel do bazy (debounced save na froncie)
    @PostMapping("/setorder")
    public ProjectModel setOptions(@RequestBody ProjectModel orderModel) {
        orderService.saveActiveOrder(orderModel);

        return orderModel;
    }
}
