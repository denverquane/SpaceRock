package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ResetFlag implements Runnable
{
  Thread resetflag;
  boolean running = true;
  //boolean reset = false;

  /*TODO: Delete this as soon as the actual register exists.*/
  private DummyRegister CR;

  public ResetFlag(String name)
  {
    resetflag = new Thread(this, name);
  }

  /**
   * Method to shut the thread down
   */
  public void shutdown()
  {
    running = false;
  }

  /*TODO: Delete DummyRegister as soon as the actual register exists.*/
  private class DummyRegister{
    public boolean resetting;
    public boolean ready;
  }

  @Override
  public void run()
  {
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

      if(CR.resetting)
      {
        CR.ready = false;
        FlagController.sensor.reset();
      }
    }

  }
}
