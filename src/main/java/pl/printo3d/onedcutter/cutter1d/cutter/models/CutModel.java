package pl.printo3d.onedcutter.cutter1d.cutter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Model zawierajacy zadeklarowana przez usera dlugosc ciecia oraz ilosc takich samych odcinkow
 * */
@Entity
public class CutModel {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  
  String cutLength;
  String cutPcs;
  String name;
  
  @ManyToOne
  //@JoinColumn(name = "cutList", referencedColumnName = "id" )
  OrderModel orderModel;

  public CutModel() {}

  
  public CutModel(String cutLength, String cutPcs) {
    this.cutLength = cutLength;
    this.cutPcs = cutPcs;
  }

  public CutModel(String cutLength, String cutPcs, String name) {
    this.cutLength = cutLength;
    this.cutPcs = cutPcs;
    this.name = name;
  }

  public String getCutLength() {
    return cutLength;
  }
  public void setCutLength(String cutLength) {
    this.cutLength = cutLength;
  }
  public String getCutPcs() {
    return cutPcs;
  }
  public void setCutPcs(String cutPcs) {
    this.cutPcs = cutPcs;
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
