package pl.printo3d.onedcutter.cutter1d.models.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


/**
 * Model zawierajacy pojedynczy surowiec o okreslonej dlugosci
 * oraz zaiwerajacy liste <double> cięć powstalych w wyniku optymalizacji<p>
 * - freeSpace() - zwraca ilosc wolnego miejsca na surowcu<p>
 * - cut() - odcina okreslony kawalek z surowca dodajac go do listy cięć<p>
 */
@Getter
@Setter
public class WorkPiece {

    private String frontID;

    private Double stockLenght;

    private List<Double> cuts = new ArrayList<Double>();
    
    private Integer patternCount;

    private Map<Double, Integer> satisfiedDemands = new HashMap<Double, Integer>();

    public WorkPiece(String frontID, Double lenght, Integer patternCount) {
        this.stockLenght = lenght;
        this.frontID = frontID;
        this.patternCount = patternCount;
    }

    public Double freeSpace(Double saw_thick) {
        Double cut_sum = 0.0;
        Double saw_sum = 0.0;
        for (int i = 0; i < cuts.size(); ++ i) {
            cut_sum += cuts.get(i);
            saw_sum += saw_thick;
        }
        return stockLenght - cut_sum - saw_sum;
    }

    public void cut(Double cutLenght) {
        cuts.add(cutLenght);
        if( satisfiedDemands.get(cutLenght) != null )
            satisfiedDemands.put(cutLenght, satisfiedDemands.get(cutLenght) + 1 );
        else
            satisfiedDemands.put(cutLenght, 1);
    }

    @Override
    public boolean equals(Object objectToCompare) {
        if (!(objectToCompare instanceof WorkPiece)) return false;

        WorkPiece patternToCompare = (WorkPiece) objectToCompare;

        return patternToCompare.stockLenght.equals(stockLenght) && patternToCompare.cuts.equals(cuts);
    }

    @Override
    public int hashCode() {
        int result=17;
        result=31*result + stockLenght.intValue();
        result=31*result+(cuts!=null ? cuts.hashCode():0);
        return result;
    }    
}
