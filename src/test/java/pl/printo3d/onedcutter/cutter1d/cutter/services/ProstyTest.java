package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.mysql.cj.xdevapi.Result;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutOptions;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OrderService;

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

    workPieces.add(new WorkPiece(1100.0));

    return workPieces;
  }
}
