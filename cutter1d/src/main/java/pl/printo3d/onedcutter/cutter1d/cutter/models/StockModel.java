package pl.printo3d.onedcutter.cutter1d.cutter.models;

public class StockModel {

  String stockLength;
  String stockPcs;
  String name;


  
  public StockModel() {
  }

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
}
