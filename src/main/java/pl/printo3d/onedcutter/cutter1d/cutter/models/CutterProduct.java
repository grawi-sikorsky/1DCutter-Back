package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa wynikowa dla CutterService->FirstFit..  Zawiera: <p>
 * 
 * {@code List<WorkPiece> workPiecesList} - czyli zoptymalizowane kawalki na stocku <p>
 * {@code List<Double> nofittedPieces} - czyli odcinki ktorych nie udalo sie zoptymalizowac [brak materialu]<p>
 */
public class CutterProduct {

    List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();
    List<Double> notFittedPieces = new ArrayList<Double>();

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
}
