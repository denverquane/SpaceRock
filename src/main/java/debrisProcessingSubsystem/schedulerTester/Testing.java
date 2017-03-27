package debrisProcessingSubsystem.schedulerTester;

import debrisProcessingSubsystem.Scheduler;
import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.updateSystem.CameraUpdate;
import debrisProcessingSubsystem.updateSystem.DebrisCollectorUpdate;
import debrisProcessingSubsystem.updateSystem.OperatorUpdate;
import debrisProcessingSubsystem.updateSystem.UpdateType;
import sensor.ZoomLevel;

import java.awt.image.BufferedImage;

/**
 * Created by Deb Rezanka on 3/27/2017.
 */
public class Testing
{
  public static void main (String args[])
  {
    //set up initial update states for each component
    CameraUpdate camUpdate = new CameraUpdate(UpdateType.CAMERA);
    DebrisCollectorUpdate debUpdate = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
    OperatorUpdate opUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    OperatorUpdate stop = new OperatorUpdate(UpdateType.STOP_SCHEDULER);

    //Set camera update params



    camUpdate.setProcessImage();
    camUpdate.setTakePicture();
    camUpdate.setZoomLevel(ZoomLevel.x4);
    camUpdate.setTurnOffCamera();
    camUpdate.setTurnOnCamera();
    System.out.println("Camera update confirmations:");
    System.out.println();

    //change debris state with update
    debUpdate.setAddDebris(true);
    debUpdate.setRawImageRequest(true);
    debUpdate.setImageName("Test Name");
    System.out.println("Debris update confirmations:");
    System.out.println();

    //Print operator state
    opUpdate.setCheckConnection();
    opUpdate.setDebris(new DebrisRecord());
    opUpdate.setDebrisTransmissionComplete();
    opUpdate.setRawImage(new BufferedImage(500, 500, 1));
    System.out.println("Operator update confirmations:");

    //start up the test
    Scheduler sched = new Scheduler();

    //Stop the scheduler
    sched.getOperator().updateComponent(stop);
  }
}
