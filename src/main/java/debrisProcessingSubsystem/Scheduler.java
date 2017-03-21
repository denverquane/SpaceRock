package debrisProcessingSubsystem;

import debrisProcessingSubsystem.debrisCollection.DebrisCollection;
import debrisProcessingSubsystem.updateSystem.Updatable;
import debrisProcessingSubsystem.updateSystem.Update;
import debrisProcessingSubsystem.updateSystem.UpdateType;

/**
 * @author Deb Rezanka
 *
 * The Scheduler will interface with the DebrisCollection, Operator,
 * and Camera objects. The Scheduler acts as a shuttle for data and
 * messages between these objects and knows nothing of the contents of the
 * Update packages.
 *
 * The Operator is sent all Updates that are not specifically for the camera or
 * the debrisCollector. Therefore the operator class will need to deal with
 * non-standard Update types.
 *
 * Created by dsr on 3/4/17.
 */
public class Scheduler
{
  private Updatable debrisCollection;
  private Updatable camera;
  private Updatable operator;
  private Worker worker;
  private Update returnedUpdate = null;
  private Update responseUpdate = null;

  /**
   * Default constructor.
   * In place now to demonstrate how components should be set up and accessed.
   * Each component should be created as an instance of Updatable
   */
  public Scheduler()
  {
    debrisCollection = new DebrisCollection();
    //include these once camera and operator implement updatable
    //camera = new Camera();
    //operator = new Operator();
    worker = new Worker();
    Thread t = new Thread(worker);
    t.start();
  }

  private class Worker implements Runnable
  {
    @Override
    public void run()
    {
      //continuous loop
      while (true)
      {
        check_Collection();
        check_Operator();
        check_Camera();
      }
    }
  }

  /**
   * check_Collection() polls the debrisCollector for update packages and
   * sends them. It will continue until the debrisCollector has no more updates to
   * send (allows constant stream of debris data to be sent to the operator to be packaged
   * for transfer).
   * If the Update reply is not null the inner loop will handle sending those Updates.
   *
   * A separate sendUpdate() method is used to determine where the Update is sent.
   *
   * The method returns when responseUpdate and returnedUpdate are both null
   */
  private void check_Collection()
  {
    while ((returnedUpdate = debrisCollection.pollComponent()) != null)
    {
      responseUpdate = sendUpdate(responseUpdate);
      while (responseUpdate != null)
      {
        responseUpdate = sendUpdate(responseUpdate);
      }
    }
  }
  /**
   * check_Operator() polls the operator for update packages and
   * sends them. It will continue until the operator has no more updates to
   * send (allows constant stream of commands to be sent to the camera)
   * If the Update reply is not null the inner loop will handle sending those Updates.
   *
   * A separate sendUpdate() method is used to determine where the Update is sent.
   *
   * The method returns when responseUpdate and returnedUpdate are both null
   *
   */
  private void check_Operator()
  {
    while ((returnedUpdate = operator.pollComponent()) != null)
    {
      responseUpdate = sendUpdate(responseUpdate);
      while (responseUpdate != null)
      {
        responseUpdate = sendUpdate(responseUpdate);
      }
    }
  }

  /**
   * check_Camera() polls the camera for update packages and
   * sends them. It will continue until the camera has no more updates to
   * send (allows constant stream of debris data to be sent to the debris collecor)
   * If the Update reply is not null the inner loop will handle sending those Updates.
   *
   * A separate sendUpdate() method is used to determine where the Update is sent.
   *
   * The method returns when responseUpdate and returnedUpdate are both null
   */
  private void check_Camera()
  {
    while ((returnedUpdate = camera.pollComponent()) != null)
    {
      responseUpdate = sendUpdate(responseUpdate);
      while (responseUpdate != null)
      {
        responseUpdate = sendUpdate(responseUpdate);
      }
    }
  }

  /**
   *
   * @param theUpdate Update package to send
   * @return response Update package from the receiver
   *
   */
  public Update sendUpdate(Update theUpdate)
  {
    Update response = null;

    if (theUpdate.getUpdateType() == UpdateType.CAMERA)
    {
      response = camera.updateComponent(theUpdate);
    }
    else if (theUpdate.getUpdateType() == UpdateType.DEBRIS_COLLECTOR)
    {
      response = debrisCollection.updateComponent(theUpdate);
    }
    else
    {
      //any odd messages, errors etc. go to the operator
      //the operator will need to deal with unknown Update types.
      responseUpdate = operator.updateComponent(theUpdate);
    }
    return response;
  }
}
