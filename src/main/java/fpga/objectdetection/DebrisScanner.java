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

  private static boolean validDebris; // if on boarder, then dont know total scope --> invalid
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
        validDebris = !(onBoarder(i, j));
        maxX = i;
        minX = i;
        maxY = j;
        minY = j;
        for (Dir d : Dir.values()) {
          search(i + d.deltaX(), j + d.deltaY());
        }
        if (validDebris) {
          int centerX = ((maxX - minX) / 2) + minX;
          int centerY = ((maxY - minY) / 2) + minY;
          int diameter = Math.max((maxX - minX) + 1, (maxY - minY) + 1);
          foundObjects.add(new Debris(centerX, centerY, diameter));
        }
      }
    }
    return foundObjects;
  }

  private static SearchValue search(int i, int j) {
    if (outOfBounds(i, j)) {
      return SearchValue.OUT_OF_BOUNDS;
    }
    if (searchedArray[i][j] == 1) {
      return SearchValue.ALREADY_SEARCHED;
    }
    searchedArray[i][j] = 1;
    if (debrisMap[i][j] == false) {
      return SearchValue.NO_DEBRIS;
    }
    if (onBoarder(i, j)) {
      validDebris = false;
    }
    trackBounds(i, j);
    for (Dir d : Dir.values()) {
      search(i + d.deltaX(), j + d.deltaY());
    }
    return SearchValue.DEBRIS_NORMAL;
  }

  private static boolean outOfBounds(int x, int y) {
    if (x < 0 || x >= debrisMap.length) {
      return true;
    }
    if (y < 0 || y >= debrisMap.length) {
      return true;
    }
    return false;
  }

  private static boolean onBoarder(int x, int y) {
    if (x == 0 || x == (debrisMap.length - 1)) {
      return true;
    }
    if (y == 0 || y == (debrisMap.length - 1)) {
      return true;
    }
    return false;
  }

  private static void trackBounds(int x, int y) {
    if (x > maxX) {
      maxX = x;
    }
    if (x < minX) {
      minX = x;
    }
    if (y > maxY) {
      maxY = y;
    }
    if (y < minY) {
      minY = y;
    }
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
