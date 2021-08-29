package pl.printo3d.onedcutter.cutter1d.cutter.models;

public class CutOptions {

  public boolean optionStackResult;
  public Double optionSzrank;


  public CutOptions() {}


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
}
