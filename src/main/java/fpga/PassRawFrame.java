package fpga;

import java.awt.image.BufferedImage;

/**
 * Created by David M on 3/4/17.
 *
 * Send Successful Frame
 * [FROM DR. ROMAN]
 * “Extract also passes a success or failure signal regarding the detection of a debris to Pass Raw Frame.
 * If success is received, the original frame received from Retrieve Image is written to the output.”
 *
 */
public class PassRawFrame
{
  public static void success(Boolean success) {
    // Does nothing!!!
    // Do not change as of 3/7/2017
  }
  public static void passFrame(BufferedImage frame){
    //TODO: send the frame to the control registers
  }

}
