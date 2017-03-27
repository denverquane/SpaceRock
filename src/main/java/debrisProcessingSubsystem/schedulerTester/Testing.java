package debrisProcessingSubsystem.schedulerTester;

import debrisProcessingSubsystem.Scheduler;
import debrisProcessingSubsystem.cameraComponent.Camera;
import debrisProcessingSubsystem.debrisCollection.DebrisCollection;
import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.operatorComponent.OperatorTesting;
import debrisProcessingSubsystem.updateSystem.*;
import sensor.ZoomLevel;

import java.awt.image.BufferedImage;

/**
 * Created by Deb Rezanka on 3/27/2017.
 */
public class Testing
{
  private static DebrisCollection debrisCollection;
  private static Camera camera;
  private static OperatorTesting operator;


  public static void main (String args[])
  {
    debrisCollection = new DebrisCollection();
    camera = new Camera();
    operator = new OperatorTesting();

    //set up initial update states for each component
    CameraUpdate camUpdate = new CameraUpdate(UpdateType.CAMERA);
    DebrisCollectorUpdate debUpdate = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
    OperatorUpdate opUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    OperatorUpdate stop = new OperatorUpdate(UpdateType.STOP_SCHEDULER);

    //Operator commands to the camera




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
    Scheduler scheduler = new Scheduler(debrisCollection, operator, camera);
}
}
