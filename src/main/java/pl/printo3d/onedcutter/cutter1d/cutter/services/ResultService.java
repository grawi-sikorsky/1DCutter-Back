package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBar;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBarPieceModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

@Service
public class ResultService {

    private ResultBar resultBar = new ResultBar();

    public ResultService() {
    }

    // TODO !!! CALA USLUGA DO PRZEROBIENIA ! 4/5 metod iteruje po "List<WorkPiece>"!

    /**
     * Otrzymuje remainPcs z CutterService i zapisuje do fullresultsow..
     * @param remainPcs
     * @return
     */
    public List<ResultBar> getRemainBars(List<Double> remainPcs) {
        List<ResultBar> remainBars = new ArrayList<ResultBar>();
        remainBars.clear();

        for (Double rp : remainPcs) {
            resultBar.addPiece(new ResultBarPieceModel((String.valueOf((rp / 1000) * 100)), String.valueOf(rp)));

            remainBars.add(new ResultBar(new ArrayList<ResultBarPieceModel>(resultBar.getResultBarPieces())));
            resultBar.clear();
        }
        //setResultRemainingPieces(remainBars);

        return remainBars;
    }

    /**
     * workpieces -> resultbars
     * @param workPieces
     * @return
     */
    public List<ResultBar> getResultsBars(List<WorkPiece> workPieces) {
        List<ResultBar> resultBars = new ArrayList<ResultBar>();
        resultBars.clear();

        for (WorkPiece wp : workPieces) {
            for (int i = 0; i < wp.getCuts().size(); ++ i) {
                resultBar.addPiece(new ResultBarPieceModel((String.valueOf((wp.getCuts().get(i) / wp.getStockLenght()) * 100)), String.valueOf(wp.getCuts().get(i))));
            }
            resultBars.add(new ResultBar(new ArrayList<ResultBarPieceModel>(resultBar.getResultBarPieces()), wp.getStockLenght()));
            resultBar.clear();
        }
        return resultBars;
    }



    public Double calculateWaste(List<WorkPiece> workPieces, ResultModel resultToChange) {
        Double resultWaste = 0.0;
        Double resultUsed = 0.0;
        Double resultWasteProcent = 0.0;

        for (WorkPiece workpc : workPieces) {
            resultUsed += workpc.getStockLenght();
            resultWaste += workpc.freeSpace(0.0);
        }
        resultWasteProcent = (resultWaste / resultUsed) * 100.0;
        resultToChange.setResultUsed(resultUsed);
        resultToChange.setResultWasteProcent(resultWasteProcent);
        resultToChange.setResultUsedProcent(100 - resultWasteProcent);

        return resultWasteProcent;
    }

    public Map<Double, Integer> calculateNeededStock(List<WorkPiece> workPieces) {
        Map<Double, Integer> resultNeededStock = new HashMap<Double, Integer>();
        resultNeededStock.clear();

        for (WorkPiece workpc : workPieces) {
            if (resultNeededStock.get(workpc.getStockLenght()) == null) resultNeededStock.put(workpc.getStockLenght(), 1);  // initial zeby nie lecialo NPE
            else resultNeededStock.put(workpc.getStockLenght(), resultNeededStock.get(workpc.getStockLenght()) + 1);
        }
        return resultNeededStock;
    }

    public Double calculatePrice(List<WorkPiece> workPieces, OrderModel incominOrderModel) {
        Double costs = 0D;
        
        for (WorkPiece workpc : workPieces) {
            for (int index = 0; index < incominOrderModel.getStockList().size(); index++) {
                if (incominOrderModel.getStockList().get(index).getIdFront().equals(workpc.getFrontID())) {
                    costs += Double.valueOf(incominOrderModel.getStockList().get(index).getStockPrice());
                }
            }
        }
        return costs;
    }

    public Integer calculateCutCount(List<WorkPiece> workPieces) {
      Integer temp=0;
      for(WorkPiece workpc : workPieces)
      {
        temp += workpc.getCuts().size();
      }
      return temp;
    }

    public ResultModel makeFullResults(List<WorkPiece> wPieces, OrderModel incominOrderModel) {
        ResultModel fullResults = new ResultModel();

        fullResults.setResultCutCount(this.calculateCutCount(wPieces));
        fullResults.setResultNeededStock(this.calculateNeededStock(wPieces));
        fullResults.setResultBars(this.getResultsBars(wPieces));
        Double resWasteProc = this.calculateWaste(wPieces, fullResults);
        fullResults.setResultWaste(resWasteProc);
        fullResults.setResultCostOveral(this.calculatePrice(wPieces,incominOrderModel));
        fullResults.setResultRemainingPieces(this.getRemainBars(remainPcs));

        return fullResults;
    }

    public void setResultRemainingPieces(List<ResultBar> remain) {
        fullResults.setResultRemainingPieces(remain);
    }
}
