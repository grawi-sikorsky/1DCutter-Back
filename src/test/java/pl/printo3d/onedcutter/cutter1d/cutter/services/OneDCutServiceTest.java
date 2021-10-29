package pl.printo3d.onedcutter.cutter1d.cutter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.printo3d.onedcutter.cutter1d.user.models.project.CutModel;
import pl.printo3d.onedcutter.cutter1d.user.models.project.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.user.models.project.WorkPiece;
import pl.printo3d.onedcutter.cutter1d.user.services.OneDCutService;
import pl.printo3d.onedcutter.cutter1d.user.services.ResultService;

@ExtendWith(MockitoExtension.class)
public class OneDCutServiceTest {

    @Mock
    ResultService resultService;

    @InjectMocks
    OneDCutService testOneDCutService;

    @Test
    public void makePartList_should_return_ktoz_to_wie(){
        //given
        List<CutModel> testCutList = new ArrayList<CutModel>();
        testCutList.add(new CutModel("260", "5"));

        List<Double> partList = new ArrayList<Double>();
        for (CutModel c : testCutList) {
            for (int i = 0; i < Integer.parseInt(c.getCutPcs()); ++ i) {
                partList.add(Double.parseDouble(c.getCutLength()));
            }
        }

        //when(testOneDCutService.makePartList(testCutList)).thenReturn(partList); // when tylko dla mockowanych obiektow, nie dla glownego testowanego.

        //when
        List<Double> testedPartList = testOneDCutService.makePartList(testCutList);

        //then
        assertEquals(testedPartList, partList);

    }

    @Test
    public void countDuplicatePatterns_should_return_true_if_find_duplicate(){
        CutterProduct dataForTest = new CutterProduct();
        CutterProduct result = new CutterProduct();
        WorkPiece wp = new WorkPiece("1", 1000.0, 1);
        WorkPiece wp2 = new WorkPiece("2", 1000.0, 1);
        WorkPiece wp3 = new WorkPiece("3", 1000.0, 1);

        wp.cut(250.0);  wp.cut(250.0);
        wp2.cut(250.0); wp2.cut(250.0);
        wp3.cut(250.0); wp3.cut(400.0);
        dataForTest.getWorkPiecesList().add( wp );
        dataForTest.getWorkPiecesList().add( wp2 );
        dataForTest.getWorkPiecesList().add( wp3 );

        result = testOneDCutService.countDuplicatePatterns(dataForTest);

        //assertTrue(result);
    }
    
}
