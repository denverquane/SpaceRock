package fpga.kenTestPackage;

import fpga.memory.MemoryMap;
import sensor.SensorInterface;
import sensor.SensorSimulation;

/**
 * Created by Ken Kressin on 13/3/17. Description:
 * This is the master controller class, used to start the FPGA flag threads, and shut them down.
 * FPGAController calls FPGAThread, passing the flag name from the Enum 'FPGAFlags' to start the
 * correct thread.
 *
 * When needed, call FPGAController.shutdown() to kill the FPGA flags in an orderly fashion.
 */
public class FPGAController {

  /*
    The camera and memory map objects will be instantiated as static objects in FPGAThread, so that
    the threads are all using the same object.
   */
  //static SensorSimulation camera = new SensorSimulation();
  //static MemoryMap memMap = new MemoryMap();
  static SensorInterface sensor;

  public FPGAController() {
    System.out.println("Starting Control Register flags...");

  }

  /**
   * Constructor which can be used to pass the camera object(s) (that implement SensorInterface)
   * to the FPGA controller, and the flag threads.  If you want to implement this, you will need to
   * decide ow you are going to store the various camera objects - probably in a Collection,
   * then change the constructor to look for whatever container you defined.
    * @param si This is the camera object the FPGA will use.
   */
/*
  public FPGAController(<camera list container>, si){
    this.sensor = si;
    System.out.println("Starting Control Register flags with new camera...");

  }
*/
  // Start all the flags.

  FPGAThread zoom = new FPGAThread("zoomThread", FPGAFlags.ZOOM);
  FPGAThread onOff = new FPGAThread("onOff", FPGAFlags.ON_OFF);
  FPGAThread reset = new FPGAThread("reset", FPGAFlags.RESET);
  FPGAThread takeImage = new FPGAThread("takeImage", FPGAFlags.TAKE_IMAGE);
  FPGAThread setGetFrame = new FPGAThread("setGetFrame", FPGAFlags.SET_GET_FRAME);
  FPGAThread imageCaptured = new FPGAThread("imageCaptured", FPGAFlags.IMAGE_CAPTURED);

  /**
   * Basic try/catch block to shut all the flags down in an orderly fashion.
   */
  public void shutDown() {
    zoom.shutdown();
    onOff.shutdown();
    reset.shutdown();
    takeImage.shutdown();
    setGetFrame.shutdown();
    imageCaptured.shutdown();
    try{
      zoom.fpgaThread.join();
      onOff.fpgaThread.join();
      reset.fpgaThread.join();
      takeImage.fpgaThread.join();
      setGetFrame.fpgaThread.join();
      imageCaptured.fpgaThread.join();

      System.out.println("Flags successfully shut down...");
    }catch(InterruptedException e){
      e.printStackTrace();
    }
  }
}