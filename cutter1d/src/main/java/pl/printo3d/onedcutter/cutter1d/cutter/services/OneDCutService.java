package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

@Service
public class OneDCutService {

  public OneDCutService() {
    cutList.add(new CutModel("260", "5"));
    cutList.add(new CutModel("135", "8"));
    cutList.add(new CutModel("750", "2"));
    stockList.add(new StockModel("2000", "5"));
  }

  @Autowired
  ResultService resultService;

  // lista roboczych kawalkow - kazdy zawiera info o cieciach oraz o ilosci wolnego miejsca na nim
  public List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();

  // Lista zawierajace dlugosci i ilosci surowca
  public List<StockModel> stockList = new ArrayList<StockModel>();

  // Lista zawierajace klucze (dlugosci) i wartosci (ilosc) formatek do ciecia
  public List<CutModel> cutList = new ArrayList<CutModel>();

  public List<Double> partsList = new ArrayList<Double>(Arrays.asList(
    320.0,350.0,370.0,320.0,350.0,370.0,320.0,350.0,370.0,320.0,350.0,370.0,
    320.0,350.0,370.0,320.0,350.0,370.0,260.0, 310.0));


  // Tworzy liste elementow do ciecia na podstawie wpisanych danych
  public List<Double> makePartList(List<CutModel> CL)
  {
    partsList.clear();
    for(CutModel c : CL) 
    {
      for(int i=0; i < Integer.parseInt(c.getCutPcs()); ++i)
      {
        partsList.add(Double.parseDouble(c.getCutLength()));
      }
    }
    return partsList;
  }

  // Sortowanie odwrotne
  public List<Double> sortReverse()
  {
    makePartList(cutList);
    Collections.sort(partsList);
    Collections.reverse(partsList);

    return partsList;
  }
  
  // 1. Pierwsza metoda rozwiazania problemu 
  public List<WorkPiece> firstFit(/* List<String> parts, List<String> stockPcs, List<String> stockLen */)
  {
    // rewers..
    sortReverse();

    // flush workpieces:
    workPiecesList.clear();

    Integer tempStockCounter=0,tempStockIterator=0;
    List<Double> partsDone = new ArrayList<Double>();
    List<Double> partsRemaining = new ArrayList<Double>(partsList);
    
    for (Double part : partsList)
    {
      System.out.println("Next part is: " + part);
      if(!workPiecesList.stream().anyMatch(work->work.freeSpace() >= part))
      {
        System.out.println("avail stocks: "+stockList.size());
        System.out.println("stock type pcs: "+stockList.get(tempStockIterator).getStockPcs());
        System.out.println("stock type counter: "+tempStockCounter);
        System.out.println("stock iterator: "+tempStockIterator);

        if( tempStockCounter < Integer.parseInt(stockList.get(tempStockIterator).getStockPcs()) )
        {
          workPiecesList.add(new WorkPiece(Double.valueOf(stockList.get(tempStockIterator).getStockLength())));
          System.out.println("No free space left, adding new stock piece: " + stockList.get(tempStockIterator).getStockLength());
          tempStockCounter++;
        }
        else
        {
          System.out.println("NOT ENOF STOK TYPE! CHECKING ODER..");
          if(tempStockIterator < stockList.size()-1)
          {
            tempStockIterator++;
            tempStockCounter=0;
          }
          else
          {
            System.out.println("NOT ENOF STOCK AT ALL!");
            partsDone.forEach(e->partsRemaining.remove(e));
            System.out.println("POZOSTALE CZESCI:");
            partsRemaining.forEach(e->System.out.println(e));
            System.out.println("--------------------------------");
            resultService.getRemainBars(partsRemaining);
            //resultService.setResultRemainingPieces(partsRemaining);
            break;
          }
        }
      }

      for(var work : workPiecesList)
      {
        if(work.freeSpace() >= part)
        {
          work.cut(part);
          System.out.println("Cutting nju pis: " + part);
          partsDone.add(part);
          break; // koniecznie wyskoczyc z loopa!
        }
      }

    }
    return workPiecesList;
  }
  
}
