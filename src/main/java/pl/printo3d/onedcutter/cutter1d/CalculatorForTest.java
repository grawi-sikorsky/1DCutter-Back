package pl.printo3d.onedcutter.cutter1d;

public class CalculatorForTest {

  public int dodaj(int i, int j) 
  {
    return i+j;
  }

  public double podziel(int a, int b)
  {
    if(b == 0)
    {
      throw new ArithmeticException("nie dzielim przez 0!");
    }
    else
      return a/b;
  }

}
