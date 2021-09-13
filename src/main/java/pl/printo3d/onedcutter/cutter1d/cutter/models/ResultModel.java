package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultModel {

  public Double resultWaste=0.0;
  private Double resultUsed=0.0;
  private Double resultWasteProcent=0.0;
  private Double resultUsedProcent=0.0;
  private Integer resultCutCount=0;
  private Double resultCostOveral=0.0;
  private Double resultCostPrecise=0.0;
  private Map<Double,Integer> resultNeededStock = new HashMap<Double,Integer>();
  private ResultAdditionalInformations resultAdditionalInformations = new ResultAdditionalInformations();

  private List<ResultBar> resultBars = new ArrayList<ResultBar>();
  private List<ResultBar> remainBars = new ArrayList<ResultBar>();          // TODO wtf? 2x to samo
  private List<ResultBar> resultRemainingPieces = new ArrayList<ResultBar>();

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
  public Map<Double, Integer> getResultNeededStock() {
    return resultNeededStock;
  }
  public void setResultNeededStock(Map<Double, Integer> resultNeededStock) {
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
  public List<ResultBar> getResultRemainingPieces() {
    return resultRemainingPieces;
  }
  public void setResultRemainingPieces(List<ResultBar> resultRemainingPieces) {
    this.resultRemainingPieces = resultRemainingPieces;
  }
  public List<ResultBar> getRemainBars() {
    return remainBars;
  }
  public void setRemainBars(List<ResultBar> remainBars) {
    this.remainBars = remainBars;
  }
  public Integer getResultCutCount() {
    return resultCutCount;
  }
  public void setResultCutCount(Integer resultCutCount) {
    this.resultCutCount = resultCutCount;
  }
  public Double getResultCostOveral() {
    return resultCostOveral;
  }
  public void setResultCostOveral(Double resultCostOveral) {
    this.resultCostOveral = resultCostOveral;
  }
  public Double getResultCostPrecise() {
    return resultCostPrecise;
  }
  public void setResultCostPrecise(Double resultCostPrecise) {
    this.resultCostPrecise = resultCostPrecise;
  }

  
}