package pl.printo3d.onedcutter.cutter1d.cutter.models;
import java.util.ArrayList;
import java.util.List;

public class ResultBar {

  public List<ResultBarPieceModel> resultBarPieces = new ArrayList<ResultBarPieceModel>();

  public ResultBar() {
  }

  public ResultBar(List<ResultBarPieceModel> resultBarPieces) {
    this.resultBarPieces = resultBarPieces;
  }


  public void addPiece(ResultBarPieceModel pieceToAdd)
  {
    resultBarPieces.add(pieceToAdd);
  }

  public void clear()
  {
    resultBarPieces.clear();
  }


  public List<ResultBarPieceModel> getResultBarPieces() {
    return resultBarPieces;
  }


  public void setResultBarPieces(List<ResultBarPieceModel> resultBarPieces) {
    this.resultBarPieces = resultBarPieces;
  }

  
}