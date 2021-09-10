package pl.printo3d.onedcutter.cutter1d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

public class CalcTest {

  @Test
  public void should_add_two_numbers()
  {
    //given
    CalculatorForTest calc = new CalculatorForTest();

    //when
    int result = calc.dodaj(4,6);

    //then
    assertEquals(result, 10);
    assertEquals(calc.dodaj(2,2), 4);
    assertEquals(calc.dodaj(-2,2), 0);
  }

  @Test
  public void should_not_add_two_numbers()
  {
    //given
    CalculatorForTest calc = new CalculatorForTest();

    //then
    assertNotEquals(calc.dodaj(2,2), 5);
    assertNotEquals(calc.dodaj(-2,2), 1);

  }
  
}
