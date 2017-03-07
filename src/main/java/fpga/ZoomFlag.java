package fpga;
import sensor.ZoomLevel;
/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ZoomFlag implements Runnable {
Thread zoomFlag;
  boolean running = true;
  private ZoomLevel currentZoom = ZoomLevel.NONE;

  public void shutdown(){
    running = false;
  }

  public ZoomFlag(String name){
    zoomFlag = new Thread(this, name);

  }
  @Override
  public void run() {
    while(running){
/**
 * TODO:  Need the interface call to check the control register's current zoom level.
 *
 */
    /* If the zoom from the control register zoom is different from currentZoom, then
       update currentZoom, and send the new zoom to the sensor interface.
       We will have to toggle Ready() to false, then update and send, then toggle ready()
       back to true to let the control register know we updated the sensor.
     */


    }

  }
}
