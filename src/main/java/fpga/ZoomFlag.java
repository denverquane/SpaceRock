package fpga;

import sensor.SensorInterface;
import sensor.ZoomLevel;
import java.util.List;
import fpga.memory.MemoryMap;
import sun.management.Sensor;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ZoomFlag implements Runnable {
  Thread zoomT;
  boolean running = true;
  private ZoomLevel currentZoom;
  private SensorInterface si;
  private MemoryMap mm;
  private boolean registerReady = false;
  private long sleepAmount = 500;


  public ZoomFlag(String name, SensorInterface si, MemoryMap mm){
    zoomT = new Thread(this, name);
    this.si = si;
    this.mm = mm;
    currentZoom = null;
  }

  @Override
  public void run() {
    while (running) {
      try {

        mm.read(Boolean.class, "zoom");
        registerReady = true;

      } catch (Exception e) {

        registerReady = false;
      }

      if(registerReady) {
        try {
          currentZoom = mm.read(ZoomLevel.class, "zoom_level");
        } catch (Exception e) {
          currentZoom = null;
        }
        if (currentZoom != null) {
          si.setZoom(currentZoom);
          registerReady = false;
        }
      }
      try {
        zoomT.sleep(sleepAmount);
      } catch (Exception e) {
        /*TODO: Handle the caught exception*/
      }
    }
  }

  public void shutdown(){
    running = false;
  }
}
