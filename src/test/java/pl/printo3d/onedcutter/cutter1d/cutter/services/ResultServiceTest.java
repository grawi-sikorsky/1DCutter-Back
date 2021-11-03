package pl.printo3d.onedcutter.cutter1d.cutter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import pl.printo3d.onedcutter.cutter1d.models.project.ResultBar;
import pl.printo3d.onedcutter.cutter1d.models.project.WorkPiece;
import pl.printo3d.onedcutter.cutter1d.services.ResultService;

public class ResultServiceTest {

    // Przygotowanie listy czesci do testow
    private List<WorkPiece> prepareWorkPiece()
    {
        List<WorkPiece> workPieces = new ArrayList<WorkPiece>();
  
        // 2 x 1000
        workPieces.add(new WorkPiece("1", 1000.0, 1));
        workPieces.add(new WorkPiece("2", 1000.0, 1));
        // 2 x 2000
        workPieces.add(new WorkPiece("3", 1000.0, 1));
        workPieces.add(new WorkPiece("4", 1000.0, 1));

        for (WorkPiece wp : workPieces) {
            wp.cut(500.0);
            wp.cut(500.0);
        }

        return workPieces;
    }

    @Test
    public void getResultsBars_should_return_correct_amount_of_resultBars() {
        // ResultService resultServiceTest = new ResultService();
        // List<ResultBar> wpTest;

        // wpTest = resultServiceTest.getResultsBars( prepareWorkPiece() );

        // assertEquals(wpTest.size(), 4);
        // assertNotEquals(wpTest.size(), 0);
    }

    @Test
    public void getResultBars_should_return_correct_amount_of_resultBar_pieces() {
        // ResultService resultServiceTest = new ResultService();
        // List<ResultBar> wpTest;
        // Integer testCount = 0;

        // wpTest = resultServiceTest.getResultsBars( prepareWorkPiece() );

        // for (ResultBar resultBar : wpTest) {
        //     testCount += resultBar.getResultBarPieces().size();
        // }

        // assertEquals(testCount, 8);
        // assertNotEquals(wpTest.size(), 0);
    }

    @Test
    public void calculateWaste_should_return_correct_value()
    {
        // ResultService resultServiceTest = new ResultService();
      
        //Double wasteTest = resultServiceTest.calculateWaste( prepareWorkPiece() );

        //assertEquals(wasteTest, 0); // 0% from prepared data
    }

    @Test
    public void calculateNeededStock_should_return_correct_value()
    {
        // ResultService resultServiceTest = new ResultService();
        // Map<Double, Integer> neededStockTest = new HashMap<Double, Integer>();
        // Integer needCountTest = 0;
        // Set<Double> needStockKeys;

        // neededStockTest = resultServiceTest.calculateNeededStock( prepareWorkPiece() );

        // needCountTest = neededStockTest.values().stream().reduce(0, Integer::sum);
        // needStockKeys = neededStockTest.keySet();

        // assertEquals(needCountTest, 4);
        // assertEquals(needStockKeys, new HashSet<Double>(Arrays.asList(1000.0)));
    }

    @Test
    public void calculateCutCount_should_return_correct_count()
    {
        // ResultService resultServiceTest = new ResultService();

        // assertEquals(resultServiceTest.calculateCutCount( prepareWorkPiece() ), 8);
    }

    


}
    //getRemainBars
