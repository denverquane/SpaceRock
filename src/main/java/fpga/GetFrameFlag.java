package fpga;

import fpga.memory.MemoryMap;
import sensor.SensorInterface;
import sun.management.Sensor;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class GetFrameFlag implements Runnable {
  Thread getFrame;
  private SensorInterface si;
  private MemoryMap mm;
  boolean running = true;
  private boolean registerReady = false;
  private int sleepAmount = 500;

  /**
   * Contructor. Gets the Sensor interface and Memory Map passed in.
   * @param name Name of thread.
   * @param si   Sensor Interface.
   * @param mm   Memory Map.
   */
  public GetFrameFlag(String name, SensorInterface si, MemoryMap mm) {
    getFrame = new Thread(this, name);
    this.si = si;
    this.mm = mm;
  }

  @Override
  public void run() {

    while(running) {

      try {

        mm.read(Boolean.class, "get_frame");
        registerReady = true;

      } catch (Exception e) {

        registerReady = false;
      }

      if(registerReady) {

        si.getFrame();
        registerReady = false;
      }
    }
  }


  /**
   * This method will allow us to shut down the thread.
   *
   */
  public void shutdown(){
    running = false;
  }
}
