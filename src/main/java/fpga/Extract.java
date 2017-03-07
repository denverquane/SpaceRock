package fpga;

import fpga.objectdetection.Debris;
import fpga.objectdetection.DebrisScanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jholland13 on 3/6/17.
 */
public class Extract {
  static List<Debris> extract(boolean[][] image) {
    List<Debris> debrisList = DebrisScanner.searchDebrisMap(image);
    DebrisScanner.printDebris(debrisList);

    return debrisList;
  }
}
