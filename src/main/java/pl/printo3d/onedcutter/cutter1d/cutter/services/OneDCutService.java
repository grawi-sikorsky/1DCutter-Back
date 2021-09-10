package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

@Service
public class OneDCutService {

  public OneDCutService() {  }

  @Autowired
  ResultService resultService;

  // lista roboczych kawalkow - kazdy zawiera info o cieciach oraz o ilosci wolnego miejsca na nim
  public List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();

  // Lista zawierajace dlugosci i ilosci surowca
  public List<StockModel> stockList = new ArrayList<StockModel>();

  // Lista zawierajace klucze (dlugosci) i wartosci (ilosc) formatek do ciecia
  public List<CutModel> cutList = new ArrayList<CutModel>();

  public List<Double> partsList = new ArrayList<Double>();


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
  public List<WorkPiece> firstFit(OrderModel order)
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
      // 1. CHWYC NOWA CZESC
      System.out.println("Next part is: " + part);

      // 2. JESLI NA OBECNYM SUROWCU NIE MA WOLNEGO MIEJSCA NA TE CZESC?
      if(!workPiecesList.stream().anyMatch(work->work.freeSpace(order.cutOptions.optionSzrank) >= part))
      {
        // 3. JESLI DOSTEPNA JEST JESZCZE JEDNA SZTUKA SUROWCA DANEGO TYPU/DLUGOSCI
        if( tempStockCounter < Integer.parseInt(stockList.get(tempStockIterator).getStockPcs()) )
        {
          // 4. DODAJ SUROWIEC DANEGO TYPU
          workPiecesList.add(new WorkPiece(Double.valueOf(stockList.get(tempStockIterator).getStockLength())));
          System.out.println("No free space left, adding new stock piece: " + stockList.get(tempStockIterator).getStockLength());
          tempStockCounter++;          
        }
        else // 5. JESLI BRAKUJE SUROWCA DANEGO TYPU:
        {
          // 6. JESTLI SA DOSTEPNE INNE TYPY/DLUGOSCI SUROWCA:
          if(tempStockIterator < stockList.size()-1)
          {
            tempStockIterator++;
            tempStockCounter=0;
            // 7. DODAJ SUROWIEC NOWEGO TYPU / ZERUJ LICZNIKI
            workPiecesList.add(new WorkPiece(Double.valueOf(stockList.get(tempStockIterator).getStockLength())));
            System.out.println("No free space left, adding new stock piece: " + stockList.get(tempStockIterator).getStockLength());
            tempStockCounter++;
          }
          else
          {
            // BRAK SUROWCA
            System.out.println("NOT ENOF STOCK AT ALL!");
          }
        }
      }

      // 8. PRZESZUKAJ LISTE UZYWANYCH SUROWCOW W POSZUKIWANIU MIEJSCA NA NOWA CZESC
      for(WorkPiece work : workPiecesList)
      {
        if(work.freeSpace(order.cutOptions.optionSzrank) >= part)
        {
          work.cut(part);
          partsDone.add(part);
          break; // koniecznie wyskoczyc z loopa!
        }
      }
    }
    // 9. STWORZ LISTE CZESCI KTORE NIE ZMIESCILY SIE NA ZADNYM SUROWCU:
    partsDone.forEach(e->partsRemaining.remove(e));

    // 10. PRZEKAZUJEMY LISTE<DOUBLE> do uslugi ktora tworzy, zapisuje i zwraca bary
    resultService.getRemainBars(partsRemaining);

    return workPiecesList;
  }
  
}
