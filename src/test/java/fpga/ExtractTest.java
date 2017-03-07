package fpga;

import static fpga.Testing.assertEquals;
import static fpga.Testing.assertTrue;

import fpga.objectdetection.Debris;
import java.io.IOException;
import java.util.List;

/**
 * Basic tests of Extract
 *
 * @author James Holland - jholland13@unm.edu
 */
public class ExtractTest extends Extract
{

  public void singleSmallDebrisTest() throws Exception {

    boolean[][] image = new boolean[300][300];
    image[50][45] = true; //Single pixel asteroid at 50x45

    List<Debris> debrisList = Extract.extract(image);


    assertEquals(new Integer(1), debrisList.size());

    Debris debris = debrisList.get(0);

    assertEquals(new Integer(50), debris.centerX);
    assertEquals(new Integer(45), debris.centerY);
    assertEquals(new Integer(1), debris.diameter);
  }

  public void multiSmallDebrisTest() throws Exception {
    boolean[][] image = new boolean[300][300];
    image[50][45] = true; //Single pixel asteroid at 50x45
    image[250][200] = true; //Single pixel asteroid at 50x45


    List<Debris> debrisList = Extract.extract(image);

    assertEquals(new Integer(2), debrisList.size());

    assertTrue(debrisList.contains(new Debris(50,45,1)));
    assertTrue(debrisList.contains(new Debris(250,200,1)));
  }

  public void cornerCaseDebrisTest() throws Exception {
    boolean[][] image = new boolean[300][300];
    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 50; j++) {
        image[i][j] = true;
      }
    }


    List<Debris> debrisList = Extract.extract(image);

    assertEquals(new Integer(1), debrisList.size());

    Debris debris = debrisList.get(0);

    assertEquals(new Integer(25), debris.centerX);
    assertEquals(new Integer(25), debris.centerY);
    assertEquals(new Integer(50), debris.diameter);
  }


  public static void main(String args[]) throws IOException{
    ExtractTest test = new ExtractTest();
    try {
      test.singleSmallDebrisTest();
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      e.printStackTrace();
    }

    try {
      test.multiSmallDebrisTest();
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      e.printStackTrace();
    }

    try {
      test.cornerCaseDebrisTest();
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      e.printStackTrace();
    }

  }

}
