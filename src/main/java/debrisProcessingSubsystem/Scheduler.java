package debrisProcessingSubsystem;

/**
 * This will be the Scheduler object shown
 * in the SADD. The Scheduler will interface with the DebrisCollection, Operator,
 * and Camera objects.
 * This is a preliminary placeholder and very subject to change.
 * Created by dsr on 3/4/17.
 */
public class Scheduler //implements DebrisCollection, Camera, Operator
{
  private class CheckCollection implements Runnable
  {
    public void run()
    {
      check_Collection();
    }
  }
  private class CheckOperator implements Runnable
  {
    public void run()
    {
      check_Operator();
    }
  }
  private class CheckCamera implements Runnable
  {
    public void run()
    {
      check_Camera();
    }
  }

  private void check_Collection()
  {

  }
  private void check_Operator()
  {

  }
  private void check_Camera()
  {

  }

}
