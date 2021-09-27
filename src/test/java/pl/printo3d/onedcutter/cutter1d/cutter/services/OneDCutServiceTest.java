package pl.printo3d.onedcutter.cutter1d.cutter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;

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
    
}
