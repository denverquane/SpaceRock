package fpga;

import java.awt.image.BufferedImage;

/**
 * Produces a boolean 2d array from an image.
 * @author James Holland
 */
class Threshold {

  /**
   * Convert an image to a 2d array of booleans
   * True indicates an debris object, False one does not exist.
   * @param image 8-bit grayscale image, must be square.
   * @return 2d array of booleans, true for debris.
   */
  static boolean[][] threshold(BufferedImage image){

    boolean[][] debrisMap = new boolean[image.getHeight()][image.getHeight()];

    for(int i = 0; i < image.getWidth(); i++){
      for(int j = 0; j < image.getHeight(); j++){
        if(image.getRGB(i, j)>= 128){
          debrisMap[i][j] = true;
        }
      }
    }
    return debrisMap;
  }

}
