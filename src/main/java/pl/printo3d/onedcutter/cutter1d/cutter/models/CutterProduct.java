package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa wynikowa dla CutterService->FirstFit..  Zawiera: <p>
 * 
 * {@code List<WorkPiece> workPiecesList} - czyli zoptymalizowane kawalki na stocku <p>
 * {@code List<Double> nofittedPieces} - czyli odcinki ktorych nie udalo sie zoptymalizowac [brak materialu]<p>
 */
public class CutterProduct {

    List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();
    List<Double> notFittedPieces = new ArrayList<Double>();
    Map<Double, Double> demandsSatisfa = new HashMap<Double, Double>();

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

    // getters setters
    public List<WorkPiece> getWorkPiecesList() {
        return workPiecesList;
    }
    public void setWorkPiecesList(List<WorkPiece> workPiecesList) {
        this.workPiecesList = workPiecesList;
    }
    public List<Double> getNotFittedPieces() {
        return notFittedPieces;
    }
    public void setNotFittedPieces(List<Double> notFittedPieces) {
        this.notFittedPieces = notFittedPieces;
    }
    public Map<Double, Double> getDemandsSatisfa() {
        return demandsSatisfa;
    }
    public void setDemandsSatisfa(Map<Double, Double> demandsSatisfa) {
        this.demandsSatisfa = demandsSatisfa;
    }
    
}
