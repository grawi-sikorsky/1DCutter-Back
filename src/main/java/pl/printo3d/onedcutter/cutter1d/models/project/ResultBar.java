package pl.printo3d.onedcutter.cutter1d.models.project;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Model zawierajacy pojedynczy wynikowy surowiec (progressbar na froncie) <p>
 * {@code List<ResultBarPieceModel> resultBarPieces} - Lista pojedynczych odcinkow na calym precie <p>
 * {@code Integer stackCount} - Ilosc takich samych pretow wynikowych - do stackowania na froncie <p>
 * {@code Double onStockLength} - Informacja o tym na jakiej dlugosci surowcu powstal ten wynikowy bar <p>
 */
@Getter
@Setter
public class ResultBar {

    private List<ResultBarPieceModel> resultBarPieces = new ArrayList<ResultBarPieceModel>();
    private Integer stackCount = 0;
    private Double onStockLength = 0.0;
    private Double freeSpaceOnStock = 0.0;

    public ResultBar() {
    }

    public ResultBar(List<ResultBarPieceModel> resultBarPieces) {
        this.resultBarPieces = resultBarPieces;
    }

    public ResultBar(List<ResultBarPieceModel> resultBarPieces, Double onStock, Integer stackCount) {
        this.resultBarPieces = resultBarPieces;
        this.stackCount = stackCount;
        this.onStockLength = onStock;
    }

    public ResultBar(List<ResultBarPieceModel> resultBarPieces, Double onStock, Integer stackCount, Double freeSpaceOnStock) {
        this.resultBarPieces = resultBarPieces;
        this.stackCount = stackCount;
        this.onStockLength = onStock;
        this.freeSpaceOnStock = freeSpaceOnStock;
    }


    public void addPiece(ResultBarPieceModel pieceToAdd) {
        resultBarPieces.add(pieceToAdd);
    }

    public void clear() {
        resultBarPieces.clear();
    }    
}