package pl.printo3d.onedcutter.cutter1d.cutter.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

/**
 * Model zawierajacy zadeklarowana przez usera dlugosc ciecia oraz ilosc takich samych odcinkow
 * */
@Entity
public class CutModel {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = true)
  Long id;
  
  String cutLength;
  String cutPcs;
  String name;


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
    if(this.cutLength != cutLength)
    {
      this.cutLength = cutLength;
    }
    
  }
  public String getCutPcs() {
    return cutPcs;
  }
  public void setCutPcs(String cutPcs) {
    this.cutPcs = cutPcs;
  }
  
}
