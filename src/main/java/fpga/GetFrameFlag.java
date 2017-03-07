package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class GetFrameFlag implements Runnable {
Thread framethread;
  boolean running = true;
  public GetFrameFlag(String name){
framethread = new Thread(this, name);
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
       * TODO:  Add the method to tell the sensor to send the requested frame.
       */
/*
   Poll the control register for a 'getFrame' request.  This request should come with new
   X and Y coordinates for the center of the frame, and a frame size.
   If new request, set our ready() to false, then send the new data to the sensor.
   Look for some sort of ack from the sensor - not sure if this should be the 'imageReady()'
   or the 'sensor.ready()' signal.  Might have to ask the sensor team for a new ack for a
   GetFrame request - something on the lines of 'frameSent()'
   Once we get confirmation, set our ready() to true.
 */


    }

  }
}
