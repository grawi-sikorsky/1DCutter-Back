package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBar;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBarPieceModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

@Service
public class ResultService {

    public ResultService() {
    }

    // TODO !!! CALA USLUGA DO PRZEROBIENIA ! 4/5 metod iteruje po "List<WorkPiece>"!

    /**
     * Tworzy RemainResultBary nie mieszczace sie na stocku
     * @param
     * @return remainBars
     */
    public List<ResultBar> getRemainBars(List<Double> notFittedPcs) {
        ResultBar notFittedBar = new ResultBar();
        List<ResultBar> remainBars = new ArrayList<ResultBar>();

        for (Double rp : notFittedPcs) {
            notFittedBar.addPiece(new ResultBarPieceModel((String.valueOf((rp / 1000) * 100)), String.valueOf(rp)));

            remainBars.add(new ResultBar(new ArrayList<ResultBarPieceModel>(notFittedBar.getResultBarPieces())));
            notFittedBar.clear();
        }
        return remainBars;
    }

    /**
     * Otrzymujac Liste WorkPiece'ow tworzy liste ResultBarsow do wyswietlenia na froncie
     * @param workPieces
     * @return
     */
    public List<ResultBar> getResultsBars(List<WorkPiece> workPieces) {
        List<ResultBar> resultBars = new ArrayList<ResultBar>();
        ResultBar resultBar = new ResultBar();

        for (WorkPiece wp : workPieces) {
            for (int i = 0; i < wp.getCuts().size(); ++ i) {
                resultBar.addPiece(new ResultBarPieceModel((String.valueOf((wp.getCuts().get(i) / wp.getStockLenght()) * 100)), String.valueOf(wp.getCuts().get(i))));
            }
            resultBars.add(new ResultBar(new ArrayList<ResultBarPieceModel>(resultBar.getResultBarPieces()), wp.getStockLenght(), wp.getPatternCount()));
            resultBar.clear();
        }
        return resultBars;
    }

    /**
     * Oblicza ilosc odpadu
     * @param workPieces
     * @param resultToChange
     * @return resultWasteProcent
     */
    public Double calculateWaste(List<WorkPiece> workPieces, ResultModel resultToChange) {
        Double resultWaste = 0.0;
        Double resultUsed = 0.0;
        Double resultWasteProcent = 0.0;

        for (WorkPiece workpc : workPieces) {
            resultUsed += workpc.getStockLenght() * workpc.getPatternCount();
            resultWaste += workpc.freeSpace(0.0) * workpc.getPatternCount();
        }
        resultWasteProcent = (resultWaste / resultUsed) * 100.0;
        resultToChange.setResultUsed(resultUsed);
        resultToChange.setResultWasteProcent(resultWasteProcent);
        resultToChange.setResultUsedProcent(100 - resultWasteProcent);

        return resultWasteProcent;
    }

    /**
     * Oblicza ilosc wymaganego stocku
     * @param workPieces
     * @return
     */
    public Map<Double, Integer> calculateNeededStock(List<WorkPiece> workPieces) {
        Map<Double, Integer> resultNeededStock = new HashMap<Double, Integer>();
        resultNeededStock.clear();

        for (WorkPiece workpc : workPieces) {
            if (resultNeededStock.get(workpc.getStockLenght()) == null) resultNeededStock.put(workpc.getStockLenght(), workpc.getPatternCount());  // initial zeby nie lecialo NPE
            else resultNeededStock.put(workpc.getStockLenght(), resultNeededStock.get(workpc.getStockLenght()) + workpc.getPatternCount());
        }
        return resultNeededStock;
    }

    /**
     * Oblicza koszt ciecia elementow z podanych cen materialu
     * @param workPieces
     * @param incominOrderModel
     * @return
     */
    public Double calculatePrice(List<WorkPiece> workPieces, OrderModel incominOrderModel) {
        Double costs = 0D;
        
        for (WorkPiece workpc : workPieces) {
            for (int index = 0; index < incominOrderModel.getStockList().size(); index++) {
                if (incominOrderModel.getStockList().get(index).getIdFront().equals(workpc.getFrontID())) {
                    costs += Double.valueOf(incominOrderModel.getStockList().get(index).getStockPrice()) * workpc.getPatternCount();
                }
            }
        }
        return costs;
    }

    /**
     * Oblicza ilosc cięć ktore trzeba wykonać
     * @param workPieces
     * @return
     */
    public Integer calculateCutCount(List<WorkPiece> workPieces) {
      Integer temp=0;
      for(WorkPiece workpc : workPieces)
      {
        temp += workpc.getCuts().size() * workpc.getPatternCount();
      }
      return temp;
    }

    /**
     * Zwraca pelne wyniki obliczen 
     * @param cutterProduct
     * @param incominOrderModel
     * @return fullResults
     */
    public ResultModel makeFullResults(CutterProduct cutterProduct, OrderModel incominOrderModel) {
        ResultModel fullResults = new ResultModel();

        fullResults.setResultCutCount(this.calculateCutCount(cutterProduct.getWorkPiecesList()));
        fullResults.setResultNeededStock(this.calculateNeededStock(cutterProduct.getWorkPiecesList()));
        fullResults.setResultBars(this.getResultsBars(cutterProduct.getWorkPiecesList()));
        Double resWasteProc = this.calculateWaste(cutterProduct.getWorkPiecesList(), fullResults); // TODO: to troche glupie jest, do poprawki kiedystam. [wewnatrz przerabia fullResults i zwraca jedna zmienna rozniez do fullResults..]
        fullResults.setResultWaste(resWasteProc);
        fullResults.setResultCostOveral(this.calculatePrice(cutterProduct.getWorkPiecesList(),incominOrderModel));
        fullResults.setResultRemainingPieces(this.getRemainBars(cutterProduct.getNotFittedPieces()));

        return fullResults;
    }
}
