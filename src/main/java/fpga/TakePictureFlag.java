package fpga;
import fpga.memory.EmptyRegisterException;
import fpga.memory.MemoryMap;
import fpga.memory.NoSuchRegisterFoundException;
import fpga.memory.UnavailbleRegisterException;
import sensor.SensorInterface;

import java.util.List;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class TakePictureFlag implements Runnable {
  Thread picThread;
  private SensorInterface si;
  private MemoryMap mm;
  boolean running = true;
  private boolean RegisterNotReady = true;
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
    /*while (ready register is not ready with data) {}*/
      while(RegisterNotReady){
          RegisterNotReady = true;
        try {

          mm.read(Boolean.class, "take_picture");
          RegisterNotReady = false;

        } catch (Exception e) {

          try {
            //sleep before next poll of register
            Thread.sleep(sleepAmount);
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          }

        }
      }

      //Have sensor take picture and wait till image processed
      SetTakePicture();

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
