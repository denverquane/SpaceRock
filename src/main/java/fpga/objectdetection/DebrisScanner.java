package fpga.objectdetection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rob on 2/25/2017.
 */

// NOTE: IS BOARDER RELIABLE DATA -- set variable for this?
// gausian filter does not provide reliable boarder data (cause it has no information to pull from edges)
public class DebrisScanner {

  private static byte[][] searchedArray;
  private static boolean[][] debrisMap;
  // SOME LIST OF DEBRIS -- not sure how this fits into greater scope
  private static ArrayList<Debris> foundObjects = new ArrayList<>();

  private static int maxX, maxY;
  private static int minX, minY;

  // SOME LIST OF DEBRIS searchDebrisMap()
  // Scan line by line (ignoring edges -- gausian filter unreliable)
  // Once we find any debris, BFS for its entirety
  // if it reaches edge, ignore
  // if it is enclosed in frame, record
  public static ArrayList<Debris> searchDebrisMap(boolean[][] debrisMap) {
    DebrisScanner.debrisMap = debrisMap;
    searchedArray = new byte[debrisMap.length][debrisMap.length];
    foundObjects = new ArrayList<>();

    for (int j = 0; j < debrisMap.length; j++) {
      for (int i = 0; i < debrisMap.length; i++) {
        if (searchedArray[i][j] == 1) {
          continue;
        }
        searchedArray[i][j] = 1;
        if (!debrisMap[i][j]) {
          continue;
        }
        maxX = i;
        minX = i;
        maxY = j;
        minY = j;
        for (Dir d : Dir.values()) {
          search(i + d.deltaX(), j + d.deltaY());
        }

        int centerX = ((maxX - minX) / 2) + minX;
        int centerY = ((maxY - minY) / 2) + minY;
        int diameter = Math.max((maxX - minX) + 1, (maxY - minY) + 1);
        foundObjects.add(new Debris(centerX, centerY, diameter));
      }
    }
    return foundObjects;
  }

  private static void search(int i, int j) {
    //Check out of bounds
    if (i < 0 || i >= debrisMap.length || j < 0 || j >= debrisMap.length) {
      return;
    }
    if (searchedArray[i][j] == 1) {
      return;
    }
    searchedArray[i][j] = 1;
    if (debrisMap[i][j] == false) {
      return;
    }

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
    for (Dir d : Dir.values()) {
      search(i + d.deltaX(), j + d.deltaY());
    }
    return;
  }

  public static List<Debris> getDebrisList(){
    return Collections.unmodifiableList(foundObjects);
  }

  public static void printList() {
    System.out.println("Found " + foundObjects.size() + " 'valid' debris");
    int counter = 1;
    for (Debris d : foundObjects) {
      System.out.println(
          "Object " + counter + " center @ (" + d.centerX + ", " + d.centerY + ") with diameter "
              + d.diameter);
      counter++;
    }
  }
}
