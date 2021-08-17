package pl.printo3d.onedcutter.cutter1d.cutter.models;

import javax.persistence.GeneratedValue;

public class StockModel {

  @GeneratedValue
  Long id;
  String stockLenght;
  String stockPcs;
  String name;

  public StockModel(String stockLenght, String cutPcs) {
    this.stockLenght = stockLenght;
    this.stockPcs = cutPcs;
  }

  public StockModel(Long id, String stockLenght, String stockPcs, String name) {
    this.id = id;
    this.stockLenght = stockLenght;
    this.stockPcs = stockPcs;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStockLenght() {
    return stockLenght;
  }

  public void setStockLenght(String stockLenght) {
    this.stockLenght = stockLenght;
  }

  public String getStockPcs() {
    return stockPcs;
  }

  public void setStockPcs(String stockPcs) {
    this.stockPcs = stockPcs;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
}
