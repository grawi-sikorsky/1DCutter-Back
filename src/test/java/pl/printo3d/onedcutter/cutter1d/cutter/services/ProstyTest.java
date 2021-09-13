package pl.printo3d.onedcutter.cutter1d.cutter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

class ProstyTest {

  @Test
  public void calculateNeededStock_count()
  {
    //given
    ResultService serviceTest = spy(ResultService.class);
    
    //when
    Map<Double,Integer> a = serviceTest.calculateNeededStock(prepareMockWorkPiece());

    //then
    assertEquals(a.size(), 2); 
  }

  @Test
  public void calculateNeededStock_test1_succes()
  {
    //given
    ResultService serviceTest = spy(ResultService.class);
    
    //when
    Map<Double,Integer> a = serviceTest.calculateNeededStock(prepareMockWorkPiece());

    //then
    assertEquals(a.size(), 2); 
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

//
    return workPieces;
  }
}
