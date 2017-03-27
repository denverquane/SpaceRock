package debrisProcessingSubsystem.operatorComponent;

import debrisProcessingSubsystem.cameraComponent.CameraStatusReport;
import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.schedulerTester.TestableComponent;
import debrisProcessingSubsystem.updateSystem.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is a testing Operator implementation.
 * Both methods currently return null.
 * Created by jdt on 3/22/17.
 * DSR added Debug code 3/23/17.
 */
public class OperatorTesting implements Updatable, TestableComponent {

  private boolean DEBUG = true;

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

    if(theUpdate.getUpdateType() == UpdateType.OPERATOR) {

      OperatorUpdate updateIn = (OperatorUpdate)theUpdate;
      HashMap paramMap = updateIn.getParamMap();

      if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.DEBRIS)){
        if (DEBUG) System.out.println("Received DEBRIS update with value " 
            + paramMap.get(OperatorUpdate.OperatorUpdateParameters.DEBRIS));
        //package and send debris back to earth.
        DebrisRecord debris = (DebrisRecord)paramMap.get(OperatorUpdate.OperatorUpdateParameters.DEBRIS);
        groundLink.sendDebris(debris);

      }
      if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.RAW_IMAGE)){
        if (DEBUG) System.out.println("Received RAW_IMAGE update with value " 
            + paramMap.get(OperatorUpdate.OperatorUpdateParameters.RAW_IMAGE));
        //package raw image and send back to earth.
        BufferedImage img = (BufferedImage) paramMap.get(OperatorUpdate.OperatorUpdateParameters.RAW_IMAGE);
        groundLink.sendImage(img);
      }
      if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.DEBRIS_TRANSMISSION_COMPLETE)){
        if (DEBUG) System.out.println("Received DEBRIS_TRANSMISSION_COMPLETE update with value " 
            + paramMap.get(OperatorUpdate.OperatorUpdateParameters.DEBRIS_TRANSMISSION_COMPLETE));
        //Notify operator that all debris has been returned.
        groundLink.notifyImageComplete();
      }
      if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.CAMERA_STATUS)){
        if (DEBUG) System.out.println("Received CAMERA_STATUS update with value " 
            + paramMap.get(OperatorUpdate.OperatorUpdateParameters.CAMERA_STATUS));
        //package and return camera status package to operator.
        CameraStatusReport cameraStatus = (CameraStatusReport)paramMap.get(OperatorUpdate.OperatorUpdateParameters.CAMERA_STATUS);
        groundLink.sendCameraStatus(cameraStatus);
      }
      /* - Update is a connection check - */
      if(paramMap.containsKey(OperatorUpdate.OperatorUpdateParameters.CHECK_CONNECTION)){
        if (DEBUG) System.out.println("Received CHECK_CONNECTION update with value " 
            + paramMap.get(OperatorUpdate.OperatorUpdateParameters.CHECK_CONNECTION));
        if(groundLink.connectionIsUp()){
          returnUpdate = new SchedulerUpdate(UpdateType.COMMUNICATION_UP);
        }
        else{
          returnUpdate = new SchedulerUpdate(UpdateType.COMMUNICATION_DOWN);
        }
      }
    }
    else System.out.println("Not an Operator Update:");
    return returnUpdate;
  }

  /**
   * Scheduler calls this method to check if any updates are available for processing.
   * @return The first element from the updateQueue.
   */
  public Update pollComponent(){
    //if(DEBUG) System.out.println("OperatorTesting polled for update, returning null");
    if(updateQueue.isEmpty()){
      return null;
    }
    else{
      Update retUpdate = updateQueue.removeFirst();
      System.out.println(retUpdate.getUpdateType());
      return retUpdate;
    }
  }

  /**
   * Puts an update in the outgoing update queue.
   * Implementation of testable component.
   * @param update Update to be sent to scheduler.
   */
  public void addUpdateForScheduler(Update update){
    updateQueue.addLast(update);
  }

  /**
   * Puts an update into the simulated debris register.
   * @param update An update to be put into the ground link's simulated debris register.
   */
  public void addUpdateAsData(Update update){
    System.err.println("No data simulated on this component");
  }
}
