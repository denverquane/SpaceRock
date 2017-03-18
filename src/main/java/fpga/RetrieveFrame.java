package fpga;

import java.awt.image.BufferedImage;

/**
 * Created by David M on 3/7/17.
 *
 * First node in Pipeline
 *
 */
public class RetrieveFrame
{
  public static void retrieveFrame(){
    BufferedImage currentFrame;
    // TODO: retrieve frame from sensor
    while(true){
      try {
        // TODO: attempt to retrieve frame
        // TODO: if frame received -> currentFrame = frame, then break out of while
        // TODO: else sleep
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // TODO: send frame to "Gaussian Filter" node

    // TODO: send frame to "Pass Raw Frame" node
  }
}
