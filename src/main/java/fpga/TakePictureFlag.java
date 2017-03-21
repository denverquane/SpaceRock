package fpga;
import fpga.memory.MemoryMap;
import sensor.SensorInterface;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class TakePictureFlag implements Runnable {
  Thread picThread;
  private SensorInterface si;
  private MemoryMap mm;
  boolean running = true;
  private boolean registerReady = false;
  private int sleepAmount = 500;


  public void SetTakePicture(){

    while(!si.ready()){

    }
    si.takePicture();

    /*Wait for image to be processed*/
    while(!si.imageReady()){
    }
  }

  /**
   * This method will allow us to shut down the thread.
   *
   */
  public void shutdown(){
    running = false;
  }


  public TakePictureFlag(String name){
    picThread = new Thread(this, name);
    si = null;

  }
  @Override
  public void run() {
    while(running) {
      //try catch to verify register ready or not. Exception thrown if no error
      try{

        mm.read(Boolean.class, "take_picture");//read register to see if ready
        registerReady = true;//exception not thrown during read of register so ready

        while(registerReady){ //register data has been read and so loop to set take picture via sensor interface begins

          try {

            SetTakePicture();//Have sensor take picture and wait till image processed

          } catch (Exception e) {

          }

          registerReady = false; //take picture complete set register ready to false to exit set picture loop
        }
      }
      catch(Exception e){
        try {
          Thread.sleep(sleepAmount); //Sensor not ready to set take picture, wait before polling again
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  /**
   * SET SENSOR
   * Choose which sensor to send instructions to,
   * to avoid problems with the constructor.
   */
  public void setSensor(SensorInterface sensor){
    si = sensor;
  }

}
