package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

public class ResultModel {

  public Double resultWaste=0.0;
  private Double resultUsed=0.0;
  private Double resultWasteProcent=0.0;
  private Double resultUsedProcent=0.0;
  private Double resultNeededStock=0.0;

  private List<ResultBar> resultBars = new ArrayList<ResultBar>();

  private List<String> result = new ArrayList<String>();
  private List<ResultBarPieceModel> resultBar = new ArrayList<ResultBarPieceModel>();

  public ResultModel(){ }




  
  public Double getResultUsedProcent() {
    return resultUsedProcent;
  }
  public void setResultUsedProcent(Double resultUsedProcent) {
    this.resultUsedProcent = resultUsedProcent;
  }
  public Double getResultUsed() {
    return resultUsed;
  }
  public void setResultUsed(Double resultUsed) {
    this.resultUsed = resultUsed;
  }
  public Double getResultWasteProcent() {
    return resultWasteProcent;
  }
  public void setResultWasteProcent(Double resultWasteProcent) {
    this.resultWasteProcent = resultWasteProcent;
  }
  public Double getResultNeededStock() {
    return resultNeededStock;
  }
  public void setResultNeededStock(Double resultNeededStock) {
    this.resultNeededStock = resultNeededStock;
  }
  public Double getResultWaste() {
    return resultWaste;
  }
  public void setResultWaste(Double resultWaste) {
    this.resultWaste = resultWaste;
  }
  public List<ResultBar> getResultBars() {
    return resultBars;
  }
  public void setResultBars(List<ResultBar> resultBars) {
    this.resultBars = resultBars;
  }
}