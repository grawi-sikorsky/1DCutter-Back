package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class OrderModel {

  public List<CutModel> cutList = new ArrayList<CutModel>();

  public List<StockModel> stockList = new ArrayList<StockModel>();

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
