package pl.printo3d.onedcutter.cutter1d.cutter.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBar;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OneDCutService;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;
import pl.printo3d.onedcutter.cutter1d.cutter.services.ResultService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OneDCutterController {

  @Autowired
  private OneDCutService cutService;

  @Autowired 
  private ResultService resultService;

  @Autowired OrderService orderService;

  @GetMapping("/1dcut")
  public List<WorkPiece> showCuts()
  {
    return cutService.firstFit();
  }

  @PostMapping("/cut")
  public ResultModel ProcessOrder(@RequestBody OrderModel orderModel)
  {
    return orderService.makeOrder(orderModel);
  }

  @GetMapping("/cut")
  public OrderModel cut()
  {
    return orderService.returnOrder();
  }

  @GetMapping("/result")
  public ResultModel result()
  {
    return resultService.makeFullResults();
  }
}
