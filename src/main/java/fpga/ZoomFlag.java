package fpga;

import sensor.SensorInterface;
import sensor.ZoomLevel;
import fpga.memory.MemoryMap;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ZoomFlag implements Runnable {
  private final int ZOOM_NULL = -999;
  Thread zoomT;
  private boolean running = true;
  private ZoomLevel currentZoom;
  private SensorInterface si;
  private boolean registerReady = false;
  private long sleepAmount = 500;
  //private MemoryMap mm;


  public ZoomFlag(String name, SensorInterface si, MemoryMap mm){
    zoomT = new Thread(this, name);
    this.si = si;
    currentZoom = null;
    //this.mm = mm;
  }

  @Override
  public void run() {
    int zoomNum = ZOOM_NULL;
    while (running) {
      try {
        zoomNum = MemoryMap.read(Integer.class, "zoom_level");
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
        Thread.sleep(sleepAmount);
      } catch (Exception e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public void shutdown(){
    running = false;
  }
}
