package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {

  public List<CutModel> cutList = new ArrayList<CutModel>();

  public List<StockModel> stockList = new ArrayList<StockModel>();

  public String usernameOrder;

  public void clearOrder()
  {
    cutList.clear();
    stockList.clear();
  }

  
  public OrderModel() {}

  public List<CutModel> getCutList() {
    return cutList;
  }
  public void setCutList(List<CutModel> cutList) {
    this.cutList = cutList;
  }
  public List<StockModel> getStockList() {
    return stockList;
  }
  public void setStockList(List<StockModel> stockList) {
    this.stockList = stockList;
  }

  
}
