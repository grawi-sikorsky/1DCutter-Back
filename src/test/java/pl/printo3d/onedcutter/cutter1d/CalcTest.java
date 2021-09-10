package pl.printo3d.onedcutter.cutter1d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

  @Test
  void should_divide_ok() 
  {
    //given
    CalculatorForTest calc = new CalculatorForTest();

    //then
    assertEquals(calc.podziel(2,2), 1);
    assertEquals(calc.podziel(100,10), 10);
  }
  
  @Test
  void should_not_divide_by_0() 
  {
    //given
    CalculatorForTest calc = new CalculatorForTest();

    //then
    assertThrows(ArithmeticException.class, () -> calc.podziel(100,0) );
  }
}