package pl.printo3d.onedcutter.cutter1d.cutter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CutOptions {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  public boolean optionStackResult;
  public Double optionSzrank;
  public boolean optionPrice;


  public CutOptions() {}

  public CutOptions(boolean optionStackResult, Double optionSzrank, boolean optionPrice) {
    this.optionStackResult = optionStackResult;
    this.optionSzrank = optionSzrank;
    this.optionPrice = optionPrice;
  }


  public boolean isOptionStackResult() {
    return optionStackResult;
  }
  public void setOptionStackResult(boolean optionStackResult) {
    this.optionStackResult = optionStackResult;
  }
  public Double getOptionSzrank() {
    return optionSzrank;
  }
  public void setOptionSzrank(Double optionSzrank) {
    this.optionSzrank = optionSzrank;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public boolean isOptionPrice() {
    return optionPrice;
  }
  public void setOptionPrice(boolean optionPrice) {
    this.optionPrice = optionPrice;
  }
  
}
