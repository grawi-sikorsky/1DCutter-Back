package pl.printo3d.onedcutter.cutter1d.cutter.models;

/**
 * Model zawierajacy zadeklarowana przez usera dlugosc ciecia oraz ilosc takich samych odcinkow
 * */
public class CutModel {

  String cutLength;
  String cutPcs;
  String name;


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
}
