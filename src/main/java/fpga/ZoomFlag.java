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
  private final int ZOOM_NULL = -999;
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
    int zoomNum = ZOOM_NULL;
    while (running) {
      try {
        zoomNum = mm.read(Integer.class, "zoom_level");
        registerReady = true;
      } catch (Exception e) {
        registerReady = false;
      }

      if(registerReady) {
        if (zoomNum != ZOOM_NULL) {
          currentZoom = ZoomLevel.fromValue(zoomNum);
          if (currentZoom != null) {
            si.setZoom(currentZoom);
            registerReady = false;
          }
        }
      }
      try {
        zoomT.sleep(sleepAmount);
      } catch (Exception e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public void shutdown(){
    running = false;
  }
}
