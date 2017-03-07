package fpga.objectdetection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rob on 2/25/2017.
 */

public class DebrisScanner {


  // SOME LIST OF DEBRIS searchDebrisMap()
  // Scan line by line
  // Once we find any debris, BFS for its entirety
  public static List<Debris> searchDebrisMap(boolean[][] debrisMap) {
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
    //Check out of bounds
    if (i < 0 || i >= debrisMap.length || j < 0 || j >= debrisMap.length) {
      return;
    }
    //Is it debris?
    if (!debrisMap[i][j]) {
      return;
    }
    //Set false to prevent rescanning.
    debrisMap[i][j] = false;

    debrisPartial.update(i, j);

    //Search adjacent cells.
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
}
