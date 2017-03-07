package fpga;
import sensor.SensorInterface;
import sensor.ZoomLevel;

import java.util.List;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ZoomFlag implements Runnable {


  private SensorInterface si;
  private MemoryMap mm;
  private ZoomLevel zoom;


  public class MemoryMap{
    public List<ZoomFlag.Reg> Registers;
  }

  public class Reg{

    public String name;
    public Boolean status;
    public String someData;

  }

  public Boolean Ready(String RegName){

    Boolean RegStatus = false;

    for(ZoomFlag.Reg r : this.mm.Registers) {
      if(r.name.equals(RegName)) {
        RegStatus = r.status;
      }
    }

    return RegStatus;
  }

  public void SetRegNotReady(String RegName){

    for(ZoomFlag.Reg r : this.mm.Registers) {
      if(r.name.equals(RegName)) {
        r.status = false;
      }
    }
  }

  public void Read_Acknowledge(){

    while(!si.ready()){

    }
    si.setZoom(zoom);
    /*TODO: Get the requested zoom*/

  }

  public ZoomFlag(String name){
    si = null;
  }
  @Override
  public void run() {

    /*TODO: set up loop in run()*/
    /*while (ready register is false) {}*/
    while(!Ready("ZoomReg")){

    }

    /*ready = false;*/
    SetRegNotReady("ZoomReg");

    /*while (ReadAck == false) {}*/
    /*TODO: find and call the appropriate ReadAck function*/
    Read_Acknowledge();
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
