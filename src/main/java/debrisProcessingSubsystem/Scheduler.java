package debrisProcessingSubsystem;

import debrisProcessingSubsystem.debrisCollection.DebrisCollection;

import java.util.LinkedList;

/**
 * This will be the Scheduler object shown
 * in the SADD. The Scheduler will interface with the DebrisCollection, Operator,
 * and Camera objects.
 * This is a preliminary placeholder and very subject to change.
 * Created by dsr on 3/4/17.
 */
public class Scheduler
{
  private Updatable debrisCollection;
  private Updatable camera;
  private Updatable operator;
  private LinkedList<Update> updateQueue;
  private Worker worker;
  private boolean connected = false;
  private Update notConnected;

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
    updateQueue = new LinkedList<>();
    connected = check_connection();
    worker = new Worker();
    Thread t = new Thread(worker);
    t.start();
  }

  private boolean check_connection()
  {
    //TODO: dsr: need a way to check if the communication system is up
    return true;
  }
  //TODO these should be in one continuous loop, checking in order.
  private class Worker implements Runnable
  {
    @Override
    public void run()
    {
      if(!connected)
      {
        //let each component know we are not connected to the ground, they can figure it out from there.
        Update cameraReply = camera.updateComponent(new CameraUpdate(UpdateType.COMMUNICATION_DOWN));
        Update operatorReply = camera.updateComponent(new OperatorUpdate(UpdateType.COMMUNICATION_DOWN));
        Update collectorReply = camera.updateComponent(new DebrisCollectorUpdate(UpdateType.COMMUNICATION_DOWN));

        if(cameraReply.getUpdateType() == UpdateType.ERROR ||
                operatorReply.getUpdateType() == UpdateType.ERROR ||
                collectorReply.getUpdateType() == UpdateType.ERROR)
        {
          //neec to deal with errors
        }


      }
      check_Collection();
      check_Operator();
      check_Camera();
    }
  }


  /**
   * Poll the Debris Collection component.
   * Filled out as example. Please change if you have a different idea.
   */
  private void check_Collection()
  {
    Update returnedUpdate = debrisCollection.pollComponent();
    //perform action according to update.
    if(returnedUpdate != null){
      updateQueue.addLast(returnedUpdate);
    }
    //else{ no update, do something else.

  }
  private void check_Operator()
  {

  }
  private void check_Camera()
  {

  }

  /**
   * This method will direct updates to the appropriate component until there
   * are no more updates to handle.
   * Filled out as example.
   * TODO this could continue for a very long time, perhapse there is a better way?
   */

  private void directUpdates(){
    Update nextUpdate;
    while(!updateQueue.isEmpty()){
      nextUpdate = updateQueue.removeFirst();
      if(nextUpdate != null){
        /*
         * if update in queue is not null, put returned update in the back of
         * the queue to be sent out to the appropriate component.
         */

        if(nextUpdate instanceof DebrisCollectorUpdate){
          updateQueue.addLast(debrisCollection.updateComponent(nextUpdate));
        }
        //TODO uncomment these when we have implementations of these components.
        else if(nextUpdate instanceof CameraUpdate){
          //updateQueue.addLast(camera.updateComponent(nextUpdate));
        }
        else if(nextUpdate instanceof OperatorUpdate){
          //updateQueue.addLast(operator.updateComponent(nextUpdate));
        }
        else{
          System.err.println("Invalid Update type");
        }
      } //else{ do nothing, dequeue next update}
    }
  }

}
