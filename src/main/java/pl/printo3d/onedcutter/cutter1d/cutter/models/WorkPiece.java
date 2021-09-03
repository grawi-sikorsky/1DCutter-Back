package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;


/**
 * Model zawierajacy pojedynczy surowiec o okreslonej dlugosci
 * oraz zaiwerajacy liste <double> cięć powstalych w wyniku optymalizacji<p>
 * - freeSpace() - zwraca ilosc wolnego miejsca na surowcu<p>
 * - cut() - odcina okreslony kawalek z surowca dodajac go do listy cięć<p>
 * */
public class WorkPiece {

  private Double stockLenght;

  public List<Double> cuts = new ArrayList<Double>();

  public WorkPiece(Double lenght)
  {
    stockLenght = lenght;
  }

  public Double freeSpace(Double saw_thick)
  {
    Double cut_sum=0.0;
    Double saw_sum=0.0;
    for (int i=0; i<cuts.size(); ++i)
    {
      cut_sum += cuts.get(i);
      saw_sum += saw_thick;
    }
    return stockLenght - cut_sum - saw_sum;
  }

  public void cut(Double cutLenght)
  {
    cuts.add(cutLenght);
  }  

  public Double getStockLenght() {
    return stockLenght;
  }

  public void setStockLenght(Double stockLenght) {
    this.stockLenght = stockLenght;
  }
}
