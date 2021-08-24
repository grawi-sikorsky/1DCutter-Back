package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    stockList.add(new StockModel("1000", "5"));
  }

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
    
    for (Double part : partsList)
    {
      System.out.println("Next part is: " + part);
      if(!workPiecesList.stream().anyMatch(work->work.freeSpace() >= part))
      {
        workPiecesList.add(new WorkPiece(Double.valueOf(stockList.get(0).getStockLength())));
        System.out.println("No free left, adding new stock piece: " + stockList.get(0).getStockLength());
      }

      for(var work : workPiecesList)
      {
        if(work.freeSpace() >= part)
        {
          work.cut(part);
          System.out.println("Cutting nju pis: " + part);
          break; // koniecznie wyskoczyc z loopa!
        }
      }
    }
    return workPiecesList;
  }
  
}
