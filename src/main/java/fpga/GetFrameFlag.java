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

  /**
   * Dummy method that simulates the getFrame method in the sensor interface.
   */
  public void getFrame()
  {

  }



  /**
   * Dummy method that simulates the checkReg method that will be implemented in the Control Registers
   */
  public boolean checkReg(String regName)
  {
    return false;
  }

  @Override
  public void run() {

    while(running)
    {
      if(checkReg("GetFrame"))
      {
        getFrame();
        /**Need more code to set GetFrame register back to false*/
      }
    }
  }

}

