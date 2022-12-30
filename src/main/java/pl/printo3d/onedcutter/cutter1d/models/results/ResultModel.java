package pl.printo3d.onedcutter.cutter1d.models.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Model zawierajacy pełny wynik optymalizacji <p>
 * {@code Double resultWaste} - Liczbowy odpad z ciecia <p>
 * {@code Double resultUsed} - Liczbowa ilosc wyk. surowca <p>
 * {@code Double resultWasteProcent} - Procent odpadu <p>
 * {@code Double resultUsedProcent} - Procent wykorzystany <p>
 * {@code Integer resultCutCount} - Ilosc ciec <p>
 * {@code Double resultCostOveral} - Koszty calkowite <p>
 * {@code Double resultCostPrecise} - Not YET.. Not YET.. <p>
 * {@code resultNeededStock} - Mapa takich samych surowcow i ich niezbednej ilosci <p>
 * {@code List<ResultBar> resultBars} - Zoptymalizowane odcinki <p>
 * {@code List<ResultBar> remainBars} - WTF? DO WYWALENIA? <p>
 * {@code List<ResultBar> resultRemainingPieces} - Niezoptymalizowane odcinki <p>
 */
@Getter
@Setter
public class ResultModel {

    private Double resultWaste = 0.0;
    private Double resultUsed = 0.0;
    private Double resultWasteProcent = 0.0;
    private Double resultUsedProcent = 0.0;
    private Integer resultCutCount = 0;
    private Double resultCostOveral = 0.0;
    private Double resultCostPrecise = 0.0;
    private Map<Double, Integer> resultNeededStock = new HashMap<Double, Integer>();

    private List<ResultBar> resultBars = new ArrayList<ResultBar>();
    private List<ResultBar> remainBars = new ArrayList<ResultBar>();
    private List<ResultBar> resultRemainingPieces = new ArrayList<ResultBar>();

    public ResultModel() {
    }

}