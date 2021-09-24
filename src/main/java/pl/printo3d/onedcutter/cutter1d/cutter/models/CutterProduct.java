package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa wynikowa dla CutterService->FirstFit..
 */
public class CutterProduct {

    List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();
    List<Double> notFittedPieces = new ArrayList<Double>();


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
