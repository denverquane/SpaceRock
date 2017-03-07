package fpga;
import sensor.ZoomLevel;
/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ZoomFlag implements Runnable {
Thread zoomFlag;
  boolean running = true;

  public void shutdown(){
    running = false;
  }

  public ZoomFlag(String name){
    zoomFlag = new Thread(this, name);

  }
  @Override
  public void run() {
    while(running){



    }

  }
}
