package fpga;

import fpga.memory.MemoryMap;
import java.util.ArrayList;
import java.util.List;
import sensor.SensorInterface;
import sensor.SensorSimulation;
import sensor.ZoomLevel;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Created by Ken Kressin on 13/3/17. Description:
 * FPGAThread is the class that will start each FPGA thread, using a switch block and the FPGAFlags
 * enum to determine what each thread will do.
 *
 */
public class FPGAThread implements Runnable {

  Thread fpgaThread;

  /*
     Create a static Memeor Map object that all the threads will access.
   */
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
  private AtomicBoolean registerReady;
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
    registerReady = new AtomicBoolean(false);
    //Set sensor to reference the camera object we want  to work on...
    sensor = camera;
  }

  /**
   * Constructor which can be used to pass multiple camera objects in a future version of the
   * simulation.  This is NOT fully implemented at this time.
   * @param name is a String used to name the thread.
   * @param inputFlag is an enum which will be used in the switch block to determine the thread's
   * behavior.  This allows one master thread class to create a thread with specific behaviors
   * depending on the FPGA flag it is emulating.
   * @param camera This is an ArrayList of camera objects that implement SensorInterface, allowing
   *
   * TODO
   * Implement multiple cameras using the arrayList in the switch block.
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

  private void setTakePicture(){
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

  private void SetZoomLevel(ZoomLevel currentZoom){
    while(!sensor.ready()){
      try {
        Thread.sleep(sleepAmount);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    /*Attempts to change the zoom level*/
    if (currentZoom != null) {
      sensor.setZoom(currentZoom);
    }

    while(!sensor.ready()){
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
   * This method toggles the camera sensor on or off, depending on the current state of the sensor.
   * If the sensor is off, onOffToggle will be showing false.  We then set onOffToggle to true, and
   * call sensor.on() to turn on the sensor.
   * If onOffToggle is true, we know the sensor is on, and we set onOffToggle to false and
   * call sensor.off() to shut the sensor down.
   */
  private void toggleOn(boolean onOff){
    if(onOff){
      sensor.on();
    }
    else{
      sensor.off();
    }
  }

  private void setFrame(int x, int y, int size){

    sensor.setFrame(x,y,size);

  }
  private synchronized <T> T isRegisterReady(String regName){
    Class<T> dataReturned = null;
    Object regObj = null;
  //private  synchronized void isRegisterReady(String regName){
    while(registerReady.get()){
      registerReady.set(false);
      try{
        regObj = MemoryMap.read(Boolean.class, regName);
        registerReady.set(true);
      }catch(Exception e){
        try{
          Thread.sleep(sleepAmount);
        }catch(InterruptedException el){
          el.printStackTrace();
        }
      }
    }
    return dataReturned.cast(regObj);
  }


  @Override
  public void run() {
    switch (flag) {
      case SET_GET_FRAME:
        int x, y, size;
        while (running) {

          x = isRegisterReady("frame_x");
          y = isRegisterReady("frame_y");
          size = isRegisterReady("frame_size");
          setFrame(x,y,size);
        }
        break;
      case ON_OFF:
        boolean onOff;
        while (running) {
          onOff = isRegisterReady("on");

          toggleOn(onOff);
        }
        break;
      case RESET:
        while (running) {
          isRegisterReady("reset");
          setReset();
        }
        break;
      case TAKE_IMAGE:
        boolean takePic;
        while(running){
          takePic = isRegisterReady("take_picture");
          if(takePic){
            setTakePicture();
          }
        }
        break;
      case ZOOM:
        /* The following are variables used only here
        * and nowhere else. */
        ZoomLevel currentZoom = null;

        while (running) {
          currentZoom = isRegisterReady("zoom_level");
          SetZoomLevel(currentZoom);
        }
        break;
      case IMAGE_CAPTURED:
        while (running) {
          /**
           * TODO:
           * Add the code to implement the image_captured flag.  This should be everything unique
           * to this flag.  I think we can add individual methods needed by the flag out of the
           * switch block.
           *
           * TODO:
           * What hook(s) does this use in SensorInterface???  Nothing there right now.
           */

        }
        break;
    }
  }
}
