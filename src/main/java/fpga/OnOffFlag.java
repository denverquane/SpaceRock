package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
import sensor.SensorSimulation;
//  Maybe will have to import the control registers class, if in different package

public class OnOffFlag implements Runnable {

  boolean sensorOn = false;
  //enum sensorOn

  Thread flagThread;
boolean running = true;

  /**
   *   Construct a new thread.
    */

  OnOffFlag(String name){
    // Create the thread
    flagThread = new Thread(this, name);

    // Start the thread
    flagThread.start();
  }

  public OnOffFlag() {

  }

  /** poll the control register looking for a change in power state.
   When detected, check sensorOn to determine whether we need to turn the
   sensor on or off.  Send the proper command to the sensor, and hange sensorOn
   to the opposite state, then go back to looking for another control register
   change in state.  By looking only for a change in the control register, we should
   be able to avoid spurrious inputs.
   **/

  /**
   * Method to shut the thread down
   */
  public void shutdown(){
    running = false;
  }


  @Override
  public void run() {

    while(running) {
    /*
       Poll the control register for change in state for the on/off register

       if(controlRegister.onOff
     */

      if (!sensorOn) {
        FlagController.sensor.on();
        sensorOn = true;
      } else {
        FlagController.sensor.off();
        sensorOn = false;
      }
      // Toggle Ready() to true
    }
  }
}
