package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class SetFrameFlag implements Runnable {
  Thread framethread;
  boolean running = true;

  public SetFrameFlag(String name){
    framethread = new Thread(this, name);
  }

  /**
   * Method to shut the thread down
   */
  public void shutdown(){
    running = false;
  }

  /**
   * Dummy method that simulates the setFrame in the sensro interface.
   */
  public void setFrame(int xPos, int yPos, int size)
  {

  }

  /**
   * Dummy method that simulates the checkReg method that will be implemented in the Control Registers
   */
  public boolean checkReg(String regName)
  {
    return false;
  }

  /**
   * Dummy method that simulates getting data from the Control Register
   */
  public int getFrameXPos()
  {
    int xPos = 100;
    return xPos;
  }

  /**
   * Dummy method that simulates getting data from the Control Register
   */
  public int getFrameYPos()
  {
    int yPos = 100;
    return yPos;
  }

  /**
   * Dummy method that simulates getting data from the Control Register
   */
  public int getFrameSize()
  {
    int frameSize = 100;
    return frameSize;
  }


  @Override
  public void run() {
    int xPos;
    int yPos;
    int size;

    while(running)
    {
      if(checkReg("GetFrame"))
      {
        xPos = getFrameXPos();
        yPos = getFrameYPos();
        size = getFrameSize();

        setFrame(xPos, yPos, size);
        /**Need more code to set GetFrame register back to false*/

      }
    }
  }

}

