package fpga;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jholland13 on 3/6/17.
 */
public class Extract
{
  public class Debris {
    public final int centerX, centerY, diameter;

    Debris(int x, int y, int dia)
    {
      centerX = x;
      centerY = y;
      diameter = dia;
    }


    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Debris debris = (Debris) o;
      if (centerX != debris.centerX) {
        return false;
      }
      if (centerY != debris.centerY) {
        return false;
      }
      return diameter == debris.diameter;
    }

    @Override
    public int hashCode() {
      int result = centerX;
      result = 31 * result + centerY;
      result = 31 * result + diameter;
      return result;
    }
  }

  static List<Debris> extract(boolean[][] image){

    return new ArrayList<>();
  }

}
