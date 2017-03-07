package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class GetFrameFlag implements Runnable {
Thread framethread;
  boolean running = true;

  /* These variables will hold the temporary values for the current frame request,
  and the last frame request, allowing us to properly update the Control registers.
   */
  private int currentX, currentY, currentSize, oldX, oldY, oldSize;


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
   Poll the control register for a 'getFrame' request.  When this is 'true', then pull the new
   X and Y coordinates and the new requested frame center from the control register.
   If new request, set our ready() to false, then send the new data to the sensor.
   Look for some sort of ack from the sensor - not sure if this should be the 'imageReady()'
   or the 'sensor.ready()' signal.  Might have to ask the sensor team for a new ack for a
   GetFrame request - something on the lines of 'frameSent()'
   Once we get confirmation, set the "lastFrameCoordinates" to the coordinates we just requested,
   then set our ready() to true.
 */
      /*
         We need the actual hooks to the control register.  Right now, I am using:
         CR.getFrame as a boolean register which will actually tell us we need to tell
         the sensor to get the next frame.
         CR.currentX - x coordinate of the center of the current requested frame.
         CR.currentY - y coordinate of the center of the current requested frame.
         CR.currentSize - size of the current requested frame.

         CR.oldX - x coordinate of the center of the last requested frame.
         CR.oldY - y coordinate of the center of the last requested frame.
         CR.oldCenter - size of the last requested frame.
       */
      if(CR.getFrame){
        //set CR.ready to false;
        currentX = CR.newX;
        currentY = CR.newY;
        currentSize = CR.newSize;

        FlagController.sensor.getFrame(currentX, currentY, currentSize);
        //wait for an ack from the sensor that the frame was sent.

        oldX = currentX;
        oldY = currentY;
        oldSize = currentSize;

        CR.oldX = oldX;
        CR.oldY = oldY;
        CR.oldSize = oldSize;
        CR.ready = true;
        CR.getFrame = false;
      }


    }

  }
}
