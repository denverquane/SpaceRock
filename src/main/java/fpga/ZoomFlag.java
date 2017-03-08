package fpga;
import sensor.SensorInterface;
import sensor.ZoomLevel;

import java.util.List;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ZoomFlag implements Runnable {
Thread zoomFlag;
  boolean running = true;
  private ZoomLevel currentZoom = ZoomLevel.NONE;

  public void shutdown(){
    running = false;
  }
  private SensorInterface si;
  private MemoryMap mm;

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

  /**
   * ZOOM LEVEL
   * Checks the first character of someData for 1, 2, 4 or 8.
   * Returns the appropriate enum.
   * Uses the default zoom level if anything weird happens.
   * @param RegName Name of the register
   * @return A ZoomLevel enum
   */
  public ZoomLevel getZoom(String RegName){
    char data;
    ZoomLevel level = ZoomLevel.NONE;
    for(ZoomFlag.Reg r : this.mm.Registers) {
      if(r.name.equals(RegName)) {
        data = r.someData.charAt(0);
        switch (data) {
          case '8': level = ZoomLevel.x8; break;
          case '4': level = ZoomLevel.x4; break;
          case '2': level = ZoomLevel.x2; break;
          default: level = ZoomLevel.NONE; break;
        }
      }
    }
    return level;
  }

  public void Read_Acknowledge(){

    while(!si.ready()){

    }
    ZoomLevel zoom = getZoom("ZoomLevel");
    si.setZoom(zoom);

  }

  public ZoomFlag(String name){
    zoomFlag = new Thread(this, name);

    si = null;
  }
  @Override
  public void run() {
    while(running){
    /* If the zoom from the control register zoom is different from currentZoom, then
       update currentZoom, and send the new zoom to the sensor interface.
       We will have to toggle Ready() to false, then update and send, then toggle ready()
       back to true to let the control register know we updated the sensor.
     */
    }

    while(!Ready("ZoomReg")) {}
    SetRegNotReady("ZoomReg");
    Read_Acknowledge();

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
