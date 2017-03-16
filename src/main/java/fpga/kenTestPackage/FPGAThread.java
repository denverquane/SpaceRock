package fpga.kenTestPackage;

import fpga.memory.MemoryMap;
import java.util.ArrayList;
import java.util.List;
import sensor.SensorInterface;
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
  /* Create a camera object that implements SensorInterface.
     For this version of the simulation, we only have one camera, so we can 'hard-code' it.
     In later versions, we would have to pass in some sort of collection of camera objects,
     all of which implement SensorInterface, then reference the camera we want to poll or control
     using a call like:
       sensor = <camera object to be controlled>;
       sensor.<SensorInterface method to be called>;
     This allows us to do all of our camera commands through the sensor interface, instead of each
     camera object.
   */
  private static SensorSimulation camera = new SensorSimulation();

  // Declare a reference to the SensorInterface.
  private static SensorInterface sensor;
  /*This list used to hold possibly multiple objects implementing SensorInterface, which would
  represent different cameras or sensors on the satellite.
   */
  private static List<SensorSimulation> cameraList = new ArrayList<SensorSimulation>();
  private boolean running = true;
  //for TakeImage Flag
  private boolean RegisterNotReady = true;
  private long sleepAmount = 500;
  private FPGAFlags flag;

  /**
   * Constructor used to build and start a flag thread using a default camera object that has
   * implemented SensorInterface.
   *
   * @param name is a String used to name the thread.
   * @param inputFlag is an enum which will be used in the switch block to determine the thread's
   * behavior.  This allows one master thread class to create a thread with specific behaviors
   * depending on the FPGA flag it is emulating.
   */
  FPGAThread(String name, FPGAFlags inputFlag) {
    fpgaThread = new Thread(this, name);
    flag = inputFlag;
    fpgaThread.start();
    //Set sensor to reference the camera object we want  to work on...
    sensor = camera;
  }

  /**
   * Constructor which can be used to pass multiple camera objects in a future version of the
   * simulation.
   * @param name is a String used to name the thread.
   * @param inputFlag is an enum which will be used in the switch block to determine the thread's
   * behavior.  This allows one master thread class to create a thread with specific behaviors
   * depending on the FPGA flag it is emulating.
   * @param camera This is an ArrayList of camera objects that implement SensorInterface, allowing
   * multiple cameras to be accessed in future versions of this simulation.
   */

  FPGAThread(String name, FPGAFlags inputFlag, List<SensorSimulation> camera){
    cameraList = camera;

  }

  /**
   * This method gives the ability to shut the thread down in an orderly manner, by setting the
   * boolean variable 'running' to false so the while block will terminate.
   */

  void shutdown() {
    running = false;
  }

  /**\
   * This method is used by TakeImage Flag to tell the camera to take a picture
   */

  private void SetTakePicture(){
    while(!sensor.ready()){
      try {
        Thread.sleep(sleepAmount);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
    sensor.takePicture();

    /* Wait for the image to be processed. */
    while(!sensor.imageReady()){
      try {
        Thread.sleep(sleepAmount);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  private void setReset(){
    while(!sensor.ready()){
      try{
        Thread.sleep(sleepAmount);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
    sensor.reset();

    /* wait for sensor to be reset*/
    while(!sensor.ready()){
      try{
        Thread.sleep(sleepAmount);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
  }
  /**
   * SET SENSOR
   * Choose which camera to send instructions to to avoid problems with the constructor.
   */
  public void setSensor(SensorInterface si){
    sensor = si;
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
          while(RegisterNotReady){
            RegisterNotReady = true;
            try{
              MemoryMap.read(Boolean.class, "reset");
              RegisterNotReady = false;
            }catch(Exception e){
              try{
                Thread.sleep(sleepAmount);
              }catch(InterruptedException el){
                el.printStackTrace();
              }
            }
          }
          setReset();
        }
        break;
      case TAKE_IMAGE:
        while (running) {
          while(RegisterNotReady){
            RegisterNotReady = true;
            try{
              MemoryMap.read(Boolean.class, "take_picture");
              RegisterNotReady = false;
            }catch(Exception e){
              try{
                //sleep before next poll of register
                Thread.sleep(sleepAmount);
              }catch (InterruptedException el){
                el.printStackTrace();
              }
            }
          }
          //Have camera take picture and wait until image processed.
          SetTakePicture();

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
