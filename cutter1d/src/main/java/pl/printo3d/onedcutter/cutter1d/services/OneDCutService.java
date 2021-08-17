package pl.printo3d.onedcutter.cutter1d.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.CutListModel;
import pl.printo3d.onedcutter.cutter1d.models.StockPiece;

@Service
public class OneDCutService {

  public OneDCutService() {
    cutList.add(new CutListModel("260", "5"));
    stockList.add(new StockListModel("1000", "5"));
  }

  // lista roboczych kawalkow - kazdy zawiera info o cieciach oraz o ilosci wolnego miejsca na nim
  public List<StockPiece> workPieces = new ArrayList<StockPiece>();

  // Lista zawierajace dlugosci i ilosci surowca
  public List<StockListModel> stockList = new ArrayList<StockListModel>();

  // Lista zawierajace klucze (dlugosci) i wartosci (ilosc) formatek do ciecia
  public List<CutListModel> cutList = new ArrayList<CutListModel>();

  public List<Double> partsList = new ArrayList<Double>(Arrays.asList(
    320.0,350.0,370.0,320.0,350.0,370.0,320.0,350.0,370.0,320.0,350.0,370.0,
    320.0,350.0,370.0,320.0,350.0,370.0,260.0, 310.0));



  // Tworzy liste elementow do ciecia na podstawie wpisanych danych
  public List<Double> makePartList(List<CutListModel> CL)
  {
    partsList.clear();
    for(CutListModel c : CL) 
    {
      for(int i=0; i < Integer.parseInt(c.getCutPcs()); ++i)
      {
        partsList.add(Double.parseDouble(c.getCutLenght()));
      }
    }
    //UserEntity ue = new UserEntity();
    //ue.setCutList(cutList);
    //us.updateUser(ue);

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
  public List<StockPiece> firstFit(/* List<String> parts, List<String> stockPcs, List<String> stockLen */)
  {
    // rewers..
    sortReverse();

    // flush workpieces:
    workPieces.clear();
    
    for (Double part : partsList)
    {
      System.out.println("Next part is: " + part);
      if(!workPieces.stream().anyMatch(work->work.freeSpace() >= part))
      {
        workPieces.add(new StockPiece(Double.valueOf(stockList.get(0).getStockLenght())));
        System.out.println("No free left, adding new stock piece: " + stockList.get(0).getStockLenght());
      }

      for(var work : workPieces)
      {
        if(work.freeSpace() >= part)
        {
          work.cut(part);
          System.out.println("Cutting nju pis: " + part);
          break; // koniecznie wyskoczyc z loopa!
        }
      }
    }
    return workPieces;
  }
  
}
