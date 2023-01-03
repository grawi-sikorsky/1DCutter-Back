package pl.printo3d.onedcutter.cutter1d.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.WorkPiece;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.results.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultBar;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultBarPieceModel;
import pl.printo3d.onedcutter.cutter1d.models.results.ResultModel;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;

@Service
public class ResultService {

    private final JwtUserDetailsService userDetailsService;

    public ResultService(JwtUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public ResultModel makeFullResults(CutterProduct cutterProduct, ProjectModel incominOrderModel) {
        ResultModel fullResults = new ResultModel();
        List<WorkPiece> workPieces = cutterProduct.getWorkPiecesList();

        this.calculateWaste(workPieces, fullResults);
        fullResults.setResultCutCount(this.calculateCutCount(workPieces));
        fullResults.setResultNeededStock(this.calculateNeededStock(workPieces));
        fullResults.setResultBars(this.getResultsBars(workPieces));
        fullResults.setResultCostOveral(this.calculatePrice(workPieces, incominOrderModel));
        fullResults.setResultRemainingPieces(this.getRemainBars(cutterProduct.getNotFittedPieces()));

        return fullResults;
    }

    /**
     * Tworzy RemainResultBary nie mieszczace sie na stocku
     * 
     * @param
     * @return remainBars
     */
    private List<ResultBar> getRemainBars(List<Double> notFittedPcs) {
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
     * Otrzymujac Liste WorkPiece'ow tworzy liste ResultBarsow do wyswietlenia na
     * froncie
     * 
     * @param workPieces
     * @return
     */
    private List<ResultBar> getResultsBars(List<WorkPiece> workPieces) {
        UserModel userModel = (UserModel) userDetailsService.loadUserByUsername(
                ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        List<ResultBar> resultBars = new ArrayList<ResultBar>();
        ResultBar resultBar = new ResultBar();

        for (WorkPiece wp : workPieces) {
            for (int i = 0; i < wp.getCuts().size(); ++i) {
                resultBar.addPiece(
                        new ResultBarPieceModel((String.valueOf((wp.getCuts().get(i) / wp.getStockLenght()) * 100)),
                                String.valueOf(wp.getCuts().get(i))));
            }
            resultBars.add(new ResultBar(new ArrayList<ResultBarPieceModel>(resultBar.getResultBarPieces()),
                    wp.getStockLenght(), wp.getPatternCount(),
                    wp.freeSpace(userModel.getActiveProjectModel().getCutOptions().getOptionSzrank())));
            resultBar.clear();
        }
        return resultBars;
    }

    /**
     * Oblicza ilosc odpadu
     * 
     * @param workPieces
     * @param resultToChange
     * @return resultWasteProcent
     */
    private Double calculateWaste(List<WorkPiece> workPieces, ResultModel resultToChange) {
        Double resultWaste = 0.0;
        Double resultUsed = 0.0;
        Double resultWasteProcent = 0.0;

        for (WorkPiece workpc : workPieces) {
            resultUsed += workpc.getStockLenght() * workpc.getPatternCount();
            resultWaste += workpc.freeSpace(0.0) * workpc.getPatternCount();
        }
        resultWasteProcent = (resultWaste / resultUsed) * 100.0;

        resultToChange.setResultWaste(resultWasteProcent);
        resultToChange.setResultWasteProcent(resultWasteProcent);
        resultToChange.setResultUsed(resultUsed);
        resultToChange.setResultUsedProcent(100 - resultWasteProcent);

        return resultWasteProcent;
    }

    /**
     * Oblicza ilosc wymaganego stocku
     * 
     * @param workPieces
     * @return
     */
    private Map<Double, Integer> calculateNeededStock(List<WorkPiece> workPieces) {
        Map<Double, Integer> resultNeededStock = new HashMap<Double, Integer>();
        resultNeededStock.clear();

        for (WorkPiece workpc : workPieces) {
            if (resultNeededStock.get(workpc.getStockLenght()) == null)
                resultNeededStock.put(workpc.getStockLenght(), workpc.getPatternCount()); // initial zeby nie lecialo
                                                                                          // NPE
            else
                resultNeededStock.put(workpc.getStockLenght(),
                        resultNeededStock.get(workpc.getStockLenght()) + workpc.getPatternCount());
        }
        return resultNeededStock;
    }

    /**
     * Oblicza koszt ciecia elementow z podanych cen materialu
     * 
     * @param workPieces
     * @param incominOrderModel
     * @return
     */
    private Double calculatePrice(List<WorkPiece> workPieces, ProjectModel incominOrderModel) {
        Double costs = 0D;

        for (WorkPiece workpc : workPieces) {
            for (int index = 0; index < incominOrderModel.getStockList().size(); index++) {
                if (incominOrderModel.getStockList().get(index).getIdFront().equals(workpc.getFrontID())) {
                    costs += Double.valueOf(incominOrderModel.getStockList().get(index).getStockPrice())
                            * workpc.getPatternCount();
                }
            }
        }
        return costs;
    }

    /**
     * Oblicza ilosc cięć ktore trzeba wykonać
     * 
     * @param workPieces
     * @return
     */
    private Integer calculateCutCount(List<WorkPiece> workPieces) {
        Integer temp = 0;
        for (WorkPiece workpc : workPieces) {
            temp += workpc.getCuts().size() * workpc.getPatternCount();
        }
        return temp;
    }
}
