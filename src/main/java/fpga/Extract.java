package fpga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Extracts a list of objects from a boolean 2d Array
 *
 * @author Rob Doyle
 * @author James Holland
 */

class Extract {
  /**
   * Extract a list of Debris objects from the image.
   * Scan the boolean map line by line.
   * Once we find any debris, BFS for its entirety.
   *
   * @param debrisMap true for debris, false for none
   *        CAUTION:destruction of boolean array will occur
   * @return List of debris with x,y centers and diameter.
   */
  static List<Debris> extract(boolean[][] debrisMap) {
    ArrayList<Debris> foundObjects = new ArrayList<>();

    for (int j = 0; j < debrisMap.length; j++) {
      for (int i = 0; i < debrisMap.length; i++) {
        if (!debrisMap[i][j]) {
          continue;
        }

        DebrisPartial debrisPartial = new DebrisPartial(i,j);
        search(i, j, debrisMap, debrisPartial);

        foundObjects.add(debrisPartial.toDebris());
      }
    }
    return Collections.unmodifiableList(foundObjects);
  }

  private static void search(int i, int j, boolean[][] debrisMap, DebrisPartial debrisPartial) {
    //Is it out of bounds?
    if (i < 0 || i >= debrisMap.length || j < 0 || j >= debrisMap.length) {
      return;
    }
    //Is it debris?
    if (!debrisMap[i][j]) {
      return;
    }
    //Set false to prevent redetection.
    debrisMap[i][j] = false;

    debrisPartial.update(i, j);

    /*
     * Search adjacent cells
     * We are forced to check all directions including up and back,
     * in the chance there is a curved object.
     */
    search(i, j-1, debrisMap, debrisPartial);
    search(i, j+1, debrisMap, debrisPartial);
    search(i+1, j, debrisMap, debrisPartial);
    search(i-1, j, debrisMap, debrisPartial);
  }

  public static void printDebris(List<Debris> list) {
    System.out.println("Found " + list.size() + " 'valid' debris");
    int counter = 1;
    for (Debris d : list) {
      System.out.println(
          "Object " + counter + " center @ (" + d.centerX + ", " + d.centerY + ") with diameter "
              + d.diameter);
      counter++;
    }
  }

  private static class DebrisPartial {
    private int minX, maxX, minY, maxY;

    private DebrisPartial(int i, int j) {
      minX = i;
      maxX = i;
      minY = j;
      maxY = j;
    }

    private void update(int i, int j) {
      if (i > maxX) {
        maxX = i;
      }
      if (i < minX) {
        minX = i;
      }
      if (j > maxY) {
        maxY = j;
      }
      if (j < minY) {
        minY = j;
      }
    }

    private Debris toDebris() {
      return new Debris(
          ((maxX - minX) / 2) + minX,
          ((maxY - minY) / 2) + minY,
          Math.max((maxX - minX) + 1, (maxY - minY) + 1));
    }
  }

  /**
   * Immutable construct for holding X,Y center and diameter.
   */
  public static class Debris {
    final int centerX, centerY, diameter;

    Debris(int x, int y, int dia) {
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
      Extract.Debris debris = (Extract.Debris) o;
      return centerX == debris.centerX && centerY == debris.centerY && diameter == debris.diameter;
    }

    @Override
    public int hashCode() {
      int result = centerX;
      result = 31 * result + centerY;
      result = 31 * result + diameter;
      return result;
    }
  }
}
