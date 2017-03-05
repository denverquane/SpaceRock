package fpga;

import sensor.SensorInterface;
/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class TakePictureFlag implements Runnable {
  private SensorInterface si;

  public TakePictureFlag(String name){
    si = null;
  }
  @Override
  public void run() {
    /*TODO: set up loop in run()*/
    /*while (ready register is false) {}*/
    /*ready = false;*/
    si.takePicture();
    /*while (ReadAck == false) {}*/
    /*TODO: find and call the appropriate ReadAck function*/
    /*ready = true;*/
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
