package debrisProcessingSubsystem.operatorComponent;

import debrisProcessingSubsystem.updateSystem.*;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is a testing Operator implementation.
 * Both methods currently return null.
 * Created by jdt on 3/22/17.
 * DSR added Debug code 3/23/17.
 */
public class OperatorTesting implements Updatable {

  private boolean DEBUG = false;

  private LinkedList<Update> updateQueue;
  private ToGroundLink groundLink;

  public OperatorTesting(){
    updateQueue = new LinkedList<>();
    groundLink = new ToGroundLink();
  }

  /**
   * The scheduler calls this to send updates to the operator component
   * @param theUpdate An Update object for the operator.
   * @return Any response from the operator.
   */
  public Update updateComponent(Update theUpdate){
    Update returnUpdate = null;

    if(DEBUG) System.out.println("OperatorTesting received update, returning null");
    if(theUpdate.getUpdateType() == UpdateType.OPERATOR) {
      OperatorUpdate updateIn = (OperatorUpdate)theUpdate;
      HashMap paramMap = updateIn.getParamMap();
      //parse update and perform action
      if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.DEBRIS)){
        //package and send debris back to earth.
      }
      else if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.RAW_IMAGE)){
        //package raw image and send back to earth.
      }
      else if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.DEBRIS_TRANSMISSION_COMPLETE)){
        //Notify operator that all debris has been returned.
      }
      else if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.CAMERA_STATUS)){
        //package and return camera status package to operator.
      }
      /* - Update is a connection check - */
      else if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.CHECK_CONNECTION)){
        if(groundLink.connectionIsUp()){
          returnUpdate = new SchedulerUpdate(UpdateType.COMMUNICATION_UP);
        }
        else{
          returnUpdate = new SchedulerUpdate(UpdateType.COMMUNICATION_DOWN);
        }
      }
    }
    return returnUpdate;
  }

  /**
   * Scheduler calls this method to check if any updates are available for processing.
   * @return The first element from the updateQueue.
   */
  public Update pollComponent(){
    if(DEBUG) System.out.println("OperatorTesting polled for update, returning null");
    if(updateQueue.isEmpty()){
      return null;
    }
    else{
      return updateQueue.removeFirst();
    }
  }
}
