package pl.printo3d.onedcutter.cutter1d.cutter.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBar;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBarPieceModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

@Service
public class ResultService {

  @Autowired
  private OneDCutService cutService;

  ResultModel fullResults = new ResultModel();

  private List<String> result = new ArrayList<String>();
  private ResultBar resultBar = new ResultBar();

  public ResultService()
  {

  }
  
  public List<String> getResults(List<WorkPiece> workPieces)
  {
    // chwilowo...
    result.clear();

    for (var work : workPieces) 
    {
      StringBuilder temp = new StringBuilder();
      temp.append("Dlugość 1000: |  ");

      for(int i=0; i<work.cuts.size(); ++i)
      {
        temp.append(work.cuts.get(i).toString() + "  |  ");
      }

      temp.append(" - odpad: " + work.freeSpace());
      
      result.add(temp.toString());
    }

    return result;
  }

// TODO: uciac wyswietlanie powtarzajacych sie barow
  public List<ResultBar> getResultsBars(List<WorkPiece> workPieces)
  {
    List<ResultBar> resultBars = new ArrayList<ResultBar>();
    resultBars.clear();

    for (WorkPiece wp : workPieces)
    {
      for(int i=0; i < wp.cuts.size(); ++i)
      {
        resultBar.addPiece(new ResultBarPieceModel((String.valueOf(  (wp.cuts.get(i) / wp.getStockLenght()) * 100)), String.valueOf(wp.cuts.get(i))));
      }
      resultBars.add(new ResultBar( new ArrayList<ResultBarPieceModel>(resultBar.resultBarPieces)  ));
      resultBar.clear();
    }

    return resultBars;
  }

  public List<ResultBar> getRemainBars(List<Double> remainPcs)
  {
    List<ResultBar> remainBars = new ArrayList<ResultBar>();
    remainBars.clear();

    for(Double rp : remainPcs)
    {
      resultBar.addPiece(new ResultBarPieceModel((String.valueOf(  (rp / 1000) * 100)), String.valueOf(rp)));

      remainBars.add(new ResultBar( new ArrayList<ResultBarPieceModel>(resultBar.resultBarPieces)  ));
      resultBar.clear();
    }
    setResultRemainingPieces(remainBars);

    return remainBars;
  }

  public Double calculateWaste(List<WorkPiece> workPieces)
  {
    Double resultWaste=0.0;
    Double resultUsed=0.0;
    Double resultWasteProcent=0.0;

    for (var workpc : workPieces) 
    {
      resultUsed += workpc.getStockLenght();
      resultWaste += workpc.freeSpace();
    }
    resultWasteProcent = (resultWaste / resultUsed) * 100.0;
    fullResults.setResultUsed(resultUsed);
    fullResults.setResultWasteProcent(resultWasteProcent);
    fullResults.setResultUsedProcent(100 - resultWasteProcent);

    return resultWasteProcent;
  }
  public void setResultRemainingPieces(List<ResultBar> remain)
  {
    fullResults.setResultRemainingPieces(remain);
  }

  public ResultModel makeFullResults()
  {
    fullResults.setResultBars(this.getResultsBars(this.cutService.workPiecesList));
    fullResults.setResultWaste(this.calculateWaste(this.cutService.workPiecesList));

    return fullResults;
  }

}
