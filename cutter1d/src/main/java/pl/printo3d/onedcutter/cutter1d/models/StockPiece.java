package pl.printo3d.onedcutter.cutter1d.models;

import java.util.ArrayList;
import java.util.List;


public class StockPiece {

  private Double stockLenght;

  public List<Double> cuts = new ArrayList<Double>();

  public StockPiece(Double lenght)
  {
    stockLenght = lenght;
  }

  public Double freeSpace()
  {
    Double cutsum=0.0;
    for (int i=0; i<cuts.size(); ++i)
    {
      cutsum += cuts.get(i);
    }
    return stockLenght - cutsum;
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
