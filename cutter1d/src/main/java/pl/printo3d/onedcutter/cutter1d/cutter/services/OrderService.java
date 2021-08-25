﻿package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;

@Service
public class OrderService {

  @Autowired
  private OneDCutService cutService;
  @Autowired
  private ResultService resultService;

  public OrderModel orderList = new OrderModel();
  
  // Lista zawierajace dlugosci i ilosci surowca
  public List<StockModel> stockList = new ArrayList<StockModel>();

  // Lista zawierajace klucze (dlugosci) i wartosci (ilosc) formatek do ciecia
  public List<CutModel> cutList = new ArrayList<CutModel>();




  public OrderModel returnOrder()
  {
    // orderList.cutList.clear();
    // orderList.stockList.clear();

    // orderList.cutList.add(new CutModel("260", "5"));
    // orderList.cutList.add(new CutModel("135", "8"));
    // orderList.cutList.add(new CutModel("780", "2"));
    // orderList.cutList.add(new CutModel("175", "4"));
    // orderList.cutList.add(new CutModel("420", "5"));
    // orderList.cutList.add(new CutModel("220", "3"));
    // orderList.stockList.add(new StockModel("1000", "4"));
    // orderList.stockList.add(new StockModel("1001", "6"));
    
    return orderList;
  }



  public ResultModel makeOrder(OrderModel orderModel) 
  {
    System.out.println("Make Order in Java");

    orderModel.stockList.forEach(e->System.out.println(e.getStockLength() + " " + e.getStockPcs()));
    orderModel.cutList.forEach(e->System.out.println(e.getCutLength() + " " + e.getCutPcs()));
    orderList.clearOrder();

    //cutService.cutList = orderList.cutList;
    //cutService.firstFit();
    cutService.cutList = orderModel.cutList;
    cutService.firstFit();

    return resultService.makeFullResults();
  }

}
