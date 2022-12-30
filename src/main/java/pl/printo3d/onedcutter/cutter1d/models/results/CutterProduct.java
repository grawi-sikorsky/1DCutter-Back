package pl.printo3d.onedcutter.cutter1d.models.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import pl.printo3d.onedcutter.cutter1d.models.WorkPiece;

/**
 * Klasa wynikowa dla CutterService->FirstFit..  Zawiera: <p>
 * 
 * {@code List<WorkPiece> workPiecesList} - czyli zoptymalizowane kawalki na stocku <p>
 * {@code List<Double> nofittedPieces} - czyli odcinki ktorych nie udalo sie zoptymalizowac [brak materialu]<p>
 */
@Getter
@Setter
public class CutterProduct {

    private List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();
    private List<Double> notFittedPieces = new ArrayList<Double>();
    private Map<Double, Double> demandsSatisfa = new HashMap<Double, Double>();

    public Integer getSolutionQuality(){
        Integer solutionQuality = 0;
        for (WorkPiece wPiece : workPiecesList) {
            solutionQuality += wPiece.getPatternCount();
        }
        return solutionQuality;
    }
    
    public Integer getSolutionVariants(){
        return workPiecesList.size();
    }    
}
