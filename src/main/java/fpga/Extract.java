package fpga;

import fpga.objectdetection.Debris;
import fpga.objectdetection.DebrisScanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jholland13 on 3/6/17.
 */
public class Extract {

  /**
   * Extract a list of Debris objects from the image
   * @param image true for debris, false for none
   * @return List of debris with x,y centers and diameter.
   */
  static List<Debris> extract(boolean[][] image) {
    List<Debris> debrisList = DebrisScanner.searchDebrisMap(image);
    DebrisScanner.printDebris(debrisList);

    return debrisList;
  }
}
