package pl.printo3d.onedcutter.cutter1d.cutter.models;

public class ResultBarPieceModel {

  private String barWithProc;
  private String barText;

  public ResultBarPieceModel(String barWithProc, String barText) {
    this.barWithProc = barWithProc;
    this.barText = barText;
  }
  
  public String getBarWithProc() {
    return barWithProc;
  }
  public void setBarWithProc(String barWithProc) {
    this.barWithProc = barWithProc;
  }
  public String getBarText() {
    return barText;
  }
  public void setBarText(String barText) {
    this.barText = barText;
  }
}
