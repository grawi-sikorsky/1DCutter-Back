package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

public class ResultModel {

  private Double resultWaste;
  private Double resultUsed;
  private Double resultWasteProcent;
  private Double resultNeededStock;

  //private List<ResultBar> resultBars = new ArrayList<ResultBar>();

  private List<String> result = new ArrayList<String>();
  private List<ResultBarPieceModel> resultBar = new ArrayList<ResultBarPieceModel>();

  public ResultModel()
  {

  }

}