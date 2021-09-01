package pl.printo3d.onedcutter.cutter1d.cutter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Model zawierajacy zadeklarowana przez usera dlugosc surowca oraz ilosc takich samych odcinkow
 * */
@Entity
public class StockModel {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String stockLength;
  String stockPcs;
  String name;

  @ManyToOne
  @JoinColumn(name = "orderModel", referencedColumnName = "id" )
  OrderModel orderModel;

  public StockModel() {}

  
  public StockModel(String stockLength, String stockPcs) {
    this.stockLength = stockLength;
    this.stockPcs = stockPcs;
  }

  public StockModel(String stockLength, String stockPcs, String name) {
    this.stockLength = stockLength;
    this.stockPcs = stockPcs;
    this.name = name;
  }

  public String getStockLength() {
    return stockLength;
  }
  public void setStockLength(String stockLength) {
    this.stockLength = stockLength;
  }
  public String getStockPcs() {
    return stockPcs;
  }
  public void setStockPcs(String stockPcs) {
    this.stockPcs = stockPcs;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public OrderModel getOrderModel() {
    return orderModel;
  }
  public void setOrderModel(OrderModel orderModel) {
    this.orderModel = orderModel;
  }

}
