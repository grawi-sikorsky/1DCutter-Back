package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

public class ResultBar {

    private List<ResultBarPieceModel> resultBarPieces = new ArrayList<ResultBarPieceModel>();
    private Integer stackCount = 0;
    private Double onStockLength = 0.0;

    public ResultBar() {
    }

    public ResultBar(List<ResultBarPieceModel> resultBarPieces) {
        this.resultBarPieces = resultBarPieces;
    }

    public ResultBar(List<ResultBarPieceModel> resultBarPieces, Double onStock) {
        this.resultBarPieces = resultBarPieces;
        this.onStockLength = onStock;
    }


    public void addPiece(ResultBarPieceModel pieceToAdd) {
        resultBarPieces.add(pieceToAdd);
    }

    public void clear() {
        resultBarPieces.clear();
    }


    public List<ResultBarPieceModel> getResultBarPieces() {
        return resultBarPieces;
    }

    public void setResultBarPieces(List<ResultBarPieceModel> resultBarPieces) {
        this.resultBarPieces = resultBarPieces;
    }

    public Integer getStackCount() {
        return stackCount;
    }

    public void setStackCount(Integer stackCount) {
        this.stackCount = stackCount;
    }

    public Double getOnStockLength() {
        return onStockLength;
    }

    public void setOnStockLength(Double onStockLength) {
        this.onStockLength = onStockLength;
    }
}