package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class OrderModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "cutList", referencedColumnName = "id" )
  public List<CutModel> cutList = new ArrayList<CutModel>();

  @OneToMany(cascade = CascadeType.ALL)
  //@JoinColumn(name = "stockList", referencedColumnName = "id" )
  public List<StockModel> stockList = new ArrayList<StockModel>();
  
  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "cutOptions", referencedColumnName = "id", unique = true, insertable = true, updatable = true)
  public CutOptions cutOptions;
  
  public String usernameOrder;

  public boolean optionStackResult;
  public Double optionSzrank;


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

  public CutOptions getCutOptions() {
    return cutOptions;
  }
  public void setCutOptions(CutOptions cutOptions) {
    this.cutOptions = cutOptions;
  }

  
}
