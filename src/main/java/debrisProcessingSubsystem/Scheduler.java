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
public class Scheduler //implements DebrisCollection, Camera, Operator
{
  private Updatable debrisCollection;
  private Updatable camera;
  private Updatable operator;
  private LinkedList<Update> updateQueue;

  /**
   * Default constructor.
   * In place now to demonstrate how components should be set up and accessed.
   * Each component should be created as an instance of Updatable
   */
  public Scheduler(){
    debrisCollection = new DebrisCollection();
    updateQueue = new LinkedList<>();
  }

  //TODO these should be in one continuous loop, checking in order.
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
    } //else{ no update, do something else.}
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
