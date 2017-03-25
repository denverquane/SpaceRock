package debrisProcessingSubsystem;

import debrisProcessingSubsystem.cameraComponent.Camera;
import debrisProcessingSubsystem.debrisCollection.DebrisCollection;
import debrisProcessingSubsystem.operatorComponent.OperatorTesting;
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
  private boolean DEBUG = true;
  private Updatable debrisCollection;
  private Updatable camera;
  private Updatable operator;
  private Worker worker;
  private Update returnedUpdate = null;
  private Update responseUpdate = null;
  private boolean runScheduler = true;

  /**
   * Default constructor.
   * In place now to demonstrate how components should be set up and accessed.
   * Each component should be created as an instance of Updatable
   */
  public Scheduler()
  {
    debrisCollection = new DebrisCollection();
    camera = new Camera();
    operator = new OperatorTesting();
    worker = new Worker();
    Thread t = new Thread(worker);
    t.setDaemon(true);
    t.start();
    if(DEBUG) System.out.println("Scheduler started");
  }

  private class Worker implements Runnable
  {
    @Override
    public void run()
    {
      //continuous loop
      while (runScheduler)
      {
        check_Collection();
        check_Operator();
        check_Camera();
      }
      return;
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
      responseUpdate = sendUpdate(returnedUpdate);
      if(DEBUG) System.out.println("Scheduler sent update");
      while (responseUpdate != null)
      {
        responseUpdate = sendUpdate(responseUpdate);
        if(DEBUG) System.out.println("Scheduler sent update");
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
      //stop scheduler
      if(returnedUpdate.getUpdateType() == UpdateType.STOP_SCHEDULER)
      {
        runScheduler = false;
        if(DEBUG) System.out.println("Scheduler Stopped");
        return;
      }
      //pause scheduler if communication is down.
      if(returnedUpdate.getUpdateType() == UpdateType.COMMUNICATION_DOWN)
      {
        if (DEBUG) System.out.println("Scheduler received COMMUNICATION_DOWN");
        try
        {
          returnedUpdate = operator.pollComponent();
          while (returnedUpdate.getUpdateType() != UpdateType.COMMUNICATION_UP)
          {
            //in case there are commands in queue between communication down and up updates
            //continue going through the queue.
            while (returnedUpdate != null && returnedUpdate.getUpdateType() != UpdateType.COMMUNICATION_UP)
            {
              returnedUpdate = sendUpdate(returnedUpdate);
              if(DEBUG) System.out.println("Scheduler sent update");
            }
            wait(10_000);
            returnedUpdate = operator.pollComponent();
          }
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
      //resume scheduler if communication us up
      responseUpdate = sendUpdate(returnedUpdate);
      if(DEBUG) System.out.println("Scheduler sent update");

      while (responseUpdate != null)
      {
        responseUpdate = sendUpdate(responseUpdate);
        if(DEBUG) System.out.println("Scheduler sent update");
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
      responseUpdate = sendUpdate(returnedUpdate);
      if(DEBUG) System.out.println("Scheduler sent update");

      while (responseUpdate != null)
      {
        responseUpdate = sendUpdate(responseUpdate);
        if(DEBUG) System.out.println("Scheduler sent update");
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
      if(DEBUG) System.out.println("Update Type = CameraUpdate");

    }
    else if (theUpdate.getUpdateType() == UpdateType.DEBRIS_COLLECTOR)
    {
      response = debrisCollection.updateComponent(theUpdate);
      if(DEBUG) System.out.println("Update Type = DebrisCollectorUpdate");
    }
    else
    {
      //any odd messages, errors etc. go to the operator
      //the operator will need to deal with unknown Update types.
      
      if(DEBUG)
      {
        operator = new OperatorTesting();
        
      }
      responseUpdate = operator.updateComponent(theUpdate);
      System.out.println("Update Type = OperatorUpdate");
    }
    return response;
  }
  //For testing purposes
  public Updatable getCamera()
  {
    return camera;
  }
  public Updatable getDebrisCollection()
  {
    return debrisCollection;
  }
  public Updatable getOperator()
  {
    return operator;
  }

}
