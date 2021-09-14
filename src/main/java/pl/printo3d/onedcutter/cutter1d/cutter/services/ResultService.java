package pl.printo3d.onedcutter.cutter1d.cutter.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBar;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBarPieceModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

@Service
public class ResultService {

  @Autowired
  private OneDCutService cutService;

  private ResultModel fullResults = new ResultModel();

  private ResultBar resultBar = new ResultBar();

  public ResultService(){}
  

  // TODO !!! CALA USLUGA DO PRZEROBIENIA ! 4/5 metod iteruje po "List<WorkPiece>"!
  // TODO DO ZEBRANIA W JEDNA METODE!

  public List<ResultBar> getResultsBars(List<WorkPiece> workPieces)
  {
    List<ResultBar> resultBars = new ArrayList<ResultBar>();
    resultBars.clear();

    // POMYSL:
    // 1 STREAM JAKOSC ZROBIC I USUNAC WSZYSTKIE DUPLIKATY
    // 2 POTEM ITEROWAC NOWO POWSTALA TABLICA I SPRAWDZIC CZY W PIERWODNEJ WYSTEPUJA TAKIE ELEMENTY I W JAKIEJ ILOSCI.
    // 3 ZROZUMIEC ZE TO WSZYSTKO NIE MA SENSU...

    for (WorkPiece wp : workPieces)
    {
      for(int i=0; i < wp.cuts.size(); ++i)
      {
        resultBar.addPiece(new ResultBarPieceModel((String.valueOf(  (wp.cuts.get(i) / wp.getStockLenght()) * 100)), String.valueOf(wp.cuts.get(i))));
      }
      resultBars.add(new ResultBar( new ArrayList<ResultBarPieceModel>(resultBar.resultBarPieces), wp.getStockLenght()  ));
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

    for (WorkPiece workpc : workPieces) 
    {
      resultUsed += workpc.getStockLenght();
      resultWaste += workpc.freeSpace(0.0);
    }
    resultWasteProcent = (resultWaste / resultUsed) * 100.0;
    fullResults.setResultUsed(resultUsed);
    fullResults.setResultWasteProcent(resultWasteProcent);
    fullResults.setResultUsedProcent(100 - resultWasteProcent);

    return resultWasteProcent;
  }

  public Map<Double,Integer> calculateNeededStock(List<WorkPiece> workPieces)
  {
    Map<Double,Integer> resultNeededStock = new HashMap<Double,Integer>();
    resultNeededStock.clear();

    for (WorkPiece workpc : workPieces)
    {
      if (resultNeededStock.get(workpc.getStockLenght()) == null) { resultNeededStock.put(workpc.getStockLenght(), 1); } // initial zeby nie lecialo NPE
      else resultNeededStock.put( workpc.getStockLenght(), resultNeededStock.get(workpc.getStockLenght())+1 );
    }
    return resultNeededStock;
  }

  public Double calculatePrice(List<WorkPiece> workPieces)
  {
    Double costs=0D;

    for (WorkPiece workpc : workPieces)
    {
      for (int index=0; index < this.cutService.stockList.size(); index++)
      {
        if( this.cutService.stockList.get(index).getIdFront().equals(workpc.getFrontID()))
        {
          costs += Double.valueOf(this.cutService.stockList.get(index).getStockPrice());
        }
      }
    }
    return costs;
  }
  


  public ResultModel makeFullResults()
  {
    fullResults.setResultCutCount(this.cutService.workPiecesList.size());
    fullResults.setResultNeededStock(this.calculateNeededStock(this.cutService.workPiecesList));
    fullResults.setResultBars(this.getResultsBars(this.cutService.workPiecesList));
    fullResults.setResultWaste(this.calculateWaste(this.cutService.workPiecesList));
    fullResults.setResultCostOveral(this.calculatePrice(this.cutService.workPiecesList));

    return fullResults;
  }

  public void setResultRemainingPieces(List<ResultBar> remain)
  {
    fullResults.setResultRemainingPieces(remain);
  }
}
