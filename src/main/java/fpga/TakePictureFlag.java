package fpga;
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


  public class MemoryMap{
    public List<Reg> Registers;
  }

  public class Reg{

    public String name;
    public Boolean status;
    public String  someData;

  }

  public Boolean Ready(String RegName){

    Boolean RegStatus = false;

    for(Reg r : this.mm.Registers) {
      if(r.name == RegName) {
        RegStatus = r.status;
      }
    }

    return RegStatus;
  }

  public void SetRegNotReady(String RegName){

    for(Reg r : this.mm.Registers) {
      if(r.name == RegName) {
        r.status = false;
      }
    }
  }

  public void Read_Acknowledge(){

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

    /*TODO: set up loop in run()*/
    /*while (ready register is false) {}*/
      while (!Ready("TakePicReg")) {

      }

    /*ready = false;*/
      SetRegNotReady("TakePicReg");

    /*while (ReadAck == false) {}*/
    /*TODO: find and call the appropriate ReadAck function*/
    /*ready = true;*/
      Read_Acknowledge();

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
