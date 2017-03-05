package fpga;
import sensor.SensorSimulation;
/**
 * Created by Ken Kressin on 4/3/17. Description:
 * This class is my idea of a 'master controller' class for the flags we are coding.
 * The idea here is to use this class to start all the flag threads and run them.
 * All the other flag classes will be written to be run as threads, implementing Runnable.
 *
 * As I envision it, he demo main method can instantiate this class to basically "power-up"
 * the FPGA.
 *
 */
public class KenFlags {

  static SensorSimulation sensor = new SensorSimulation();
  public KenFlags(){

    ZoomFlag zoom = new ZoomFlag("flag1");
    OnOffFlag onOff = new OnOffFlag("flag2");
    // SetFrameFlag setFrame = new SetFrameFlag("flag3");
    ResetFlag reset = new ResetFlag("flag4");
    TakePictureFlag takePic = new TakePictureFlag("flag5");
    GetFrameFlag frameFlag = new GetFrameFlag("flag6");

  }
}
