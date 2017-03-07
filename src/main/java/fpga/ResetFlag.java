package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ResetFlag implements Runnable {
Thread resetflag;
  boolean running = true;
  //boolean reset = false;

public ResetFlag(String name){
resetflag = new Thread(this, name);
}

  /**
   * Method to shut the thread down
   */
  public void shutdown(){
    running = false;
  }

  @Override
  public void run() {
    while(running){
/**
 * TODO:  We need the control register name to call for the reset command.
 */
    /*
       poll the control register looking for a signal indicating we are resetting the
       sensor - possibly a boolean?
       When true, we set our ready() to false, then send the reset signal to the sensor.
       When the sensor replies with a sensor ready, then we set our ready() method to true.
     */

    }

  }
}
