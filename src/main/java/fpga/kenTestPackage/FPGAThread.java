package fpga.kenTestPackage;

import fpga.memory.MemoryMap;
import sensor.SensorSimulation;

/**
 * Created by Ken Kressin on 13/3/17. Description:
 * FPGAThread is the class that will start each FPGA thread, using a switch block and the FPGAFlags
 * enum to determine what each thread will do.
 *
 *
 */
public class FPGAThread implements Runnable {

  Thread fpgaThread;
  static MemoryMap mm = new MemoryMap();
  static SensorSimulation sensor = new SensorSimulation();
  private boolean running = true;
  FPGAFlags flag;

  /**
   * This method gives the ability to shut the thread down in an oredly manner, by setting the
   * boolean variable 'running' to false so the while block will terminate.
   */
  public void shutdown() {
    running = false;
  }

  /**
   * Constructor used to build and start a flag thread.
   *
   * @param name is a String used to name the thread.
   * @param inputFlag is an enum which will be used in the switch block to determine the thread's
   * behavior.  This allows ond master thread class to create a thread with specific behaviors
   * depending on the FPGA flag it is emulating.
   */
  FPGAThread(String name, FPGAFlags inputFlag) {
    fpgaThread = new Thread(this, name);
    flag = inputFlag;
    fpgaThread.start();
  }

  @Override
  public void run() {

    switch (flag) {
      case SET_GET_FRAME:
        while (running) {
          /**
           * TODO:
           * Add the code to implement the get/set frame flag.  This should be everything unique
           * to this flag.  I think we can add individual methods needed by the flag out of the
           * switch block.
           */

        }
        break;
      case ON_OFF:
        while (running) {
          /**
           * TODO:
           * Add the code to implement the on/off flag.  This should be everything unique
           * to this flag.  I think we can add individual methods needed by the flag out of the
           * switch block.
           */

        }
        break;
      case RESET:
        while (running) {
          /**
           * TODO:
           * Add the code to implement the reset flag.  This should be everything unique
           * to this flag.  I think we can add individual methods needed by the flag out of the
           * switch block.
           */

        }
        break;
      case TAKE_IMAGE:
        while (running) {
          /**
           * TODO:
           * Add the code to implement the take image flag.  This should be everything unique
           * to this flag.  I think we can add individual methods needed by the flag out of the
           * switch block.
           */

        }
        break;
      case ZOOM:
        while (running) {
          /**
           * TODO:
           * Add the code to implement the zoom flag.  This should be everything unique
           * to this flag.  I think we can add individual methods needed by the flag out of the
           * switch block.
           */

        }
        break;
      case IMAGE_CAPTURED:
        while (running) {
          /**
           * TODO:
           * Add the code to implement the image_captured flag.  This should be everything unique
           * to this flag.  I think we can add individual methods needed by the flag out of the
           * switch block.
           */

        }
        break;
    }
  }
}
