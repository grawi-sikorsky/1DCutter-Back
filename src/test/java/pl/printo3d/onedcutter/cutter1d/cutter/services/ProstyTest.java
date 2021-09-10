package pl.printo3d.onedcutter.cutter1d.cutter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

class ProstyTest {

  @Test
  public void calculateNeededStock_mapReturn()
  {
    //given
    ResultService serviceTest = spy(ResultService.class);
    
    //when
    Map<Double,Integer> a = serviceTest.calculateNeededStock(prepareMockWorkPiece());

    //then
    assertEquals(a.size(), 2); // map (key: 1000, val: 2) i (key: 2000, val 2)
  }
  private List<WorkPiece> prepareMockWorkPiece()
  {
    List<WorkPiece> workPieces = new ArrayList<WorkPiece>();
    // 2 x 1000
    workPieces.add(new WorkPiece(1000.0));
    workPieces.add(new WorkPiece(1000.0));
    // 2 x 2000
    workPieces.add(new WorkPiece(2000.0));
    workPieces.add(new WorkPiece(2000.0));

    return workPieces;
  }

  @Test
  public void calculateWaste()
  {
    //given
    ResultService serviceTest = spy(ResultService.class);
    
    //when
    Double result = serviceTest.calculateWaste( prepareMockWorkPiece2() );

    //then
    assertEquals(result, 10); // 100 - 90 = 10%
  }
  private List<WorkPiece> prepareMockWorkPiece2()
  {
    List<WorkPiece> workPieces = new ArrayList<WorkPiece>();
    workPieces.add(new WorkPiece(100.0));
    workPieces.add(new WorkPiece(100.0));
    workPieces.get(0).cut(90.0);
    workPieces.get(1).cut(90.0);

    return workPieces;
  }


}
