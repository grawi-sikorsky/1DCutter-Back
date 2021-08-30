package pl.printo3d.onedcutter.cutter1d.cutter.services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

  private ResultBar resultBar = new ResultBar();

  public ResultService()
  {

  }
  

// TODO: uciac wyswietlanie powtarzajacych sie barow
  public List<ResultBar> getResultsBars(List<WorkPiece> workPieces)
  {
    List<ResultBar> arej2 = new ArrayList<ResultBar>();
    List<Double> compArray = new ArrayList<Double>();
    Integer count=0;

    List<ResultBar> resultBars = new ArrayList<ResultBar>();
    resultBars.clear();
    //compArray.clear();

    // POMYSL:
    // 1 STREAM JAKOSC ZROBIC I USUNAC WSZYSTKIE DUPLIKATY
    // 2 POTEM ITEROWAC NOWO POWSTALA TABLICA I SPRAWDZIC CZY W PIERWODNEJ WYSTEPUJA TAKIE ELEMENTY I W JAKIEJ ILOSCI.
    // 3 ZROZUMIEC ZE TO WSZYSTKO NIE MA SENSU...

    workPieces.stream()
        .filter(i -> Collections.frequency(workPieces.gety, i) > 1)
        //Collect elements to a Set and print out the values 
        .collect(Collectors.toSet())
        .forEach(System.out::println);

  

    for (WorkPiece wp : workPieces)
    {
      for(int i=0; i < wp.cuts.size(); ++i)
      {
        resultBar.addPiece(new ResultBarPieceModel((String.valueOf(  (wp.cuts.get(i) / wp.getStockLenght()) * 100)), String.valueOf(wp.cuts.get(i))));
      }
      resultBars.add(new ResultBar( new ArrayList<ResultBarPieceModel>(resultBar.resultBarPieces)  ));
      arej2.add( new ResultBar( new ArrayList<ResultBarPieceModel>(resultBar.resultBarPieces) ));
      resultBar.clear();
    }
    
    // liczymy duplikaty:
    System.out.println("DUPLIKATY: ");
    System.out.println(count);
    return resultBars;
  }
  public boolean compare()
  {
    
    return true;
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
