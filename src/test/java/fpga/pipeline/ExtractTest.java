package fpga.pipeline;

import static fpga.Testing.assertEquals;
import static fpga.Testing.assertTrue;

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

    List<Extract.Debris> debrisList = Extract.extract(image);


    assertEquals(new Integer(1), debrisList.size());

    Extract.Debris debris = debrisList.get(0);

    assertEquals(new Integer(50), debris.centerX);
    assertEquals(new Integer(45), debris.centerY);
    assertEquals(new Integer(1), debris.diameter);
  }

  public void multiSmallDebrisTest() throws Exception {
    boolean[][] image = new boolean[300][300];
    image[50][45] = true; //Single pixel asteroid at 50x45
    image[250][200] = true; //Single pixel asteroid at 50x45


    List<Extract.Debris> debrisList = Extract.extract(image);

    assertEquals(new Integer(2), debrisList.size());

    assertTrue(debrisList.contains(new Extract.Debris(50,45,1)));
    assertTrue(debrisList.contains(new Extract.Debris(250,200,1)));
  }

  public void cornerCaseDebrisTest() throws Exception {
    boolean[][] image = new boolean[300][300];
    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 50; j++) {
        image[i][j] = true;
      }
    }


    List<Extract.Debris> debrisList = Extract.extract(image);

    assertEquals(new Integer(1), debrisList.size());

    Extract.Debris debris = debrisList.get(0);

    assertEquals(new Integer(24), debris.centerX);
    assertEquals(new Integer(24), debris.centerY);
    assertEquals(new Integer(50), debris.diameter);
  }


  public static void main(String args[]) throws IOException{
    ExtractTest test = new ExtractTest();
    try {
      test.singleSmallDebrisTest();
      System.err.println("Test 1 passed");
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      e.printStackTrace();
    }

    try {
      test.multiSmallDebrisTest();
      System.err.println("Test 2 passed");
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      e.printStackTrace();
    }

    try {
      test.cornerCaseDebrisTest();
      System.err.println("Test 3 passed");
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      e.printStackTrace();
    }

  }

}
