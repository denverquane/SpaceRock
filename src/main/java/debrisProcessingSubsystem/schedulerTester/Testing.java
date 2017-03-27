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
    DebrisCollectorUpdate debUpdate = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
    OperatorUpdate opUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    OperatorUpdate stop = new OperatorUpdate(UpdateType.STOP_SCHEDULER);

    //Operator commands to the camera
    CameraUpdate camUpdate = new CameraUpdate(UpdateType.CAMERA);
    camUpdate.setTurnOnCamera();
    operator.addUpdateForScheduler(camUpdate);
    CameraUpdate camUpdate2 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate2.setResetCamera();
    operator.addUpdateForScheduler(camUpdate2);
    CameraUpdate camUpdate3 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate3.setZoomLevel(ZoomLevel.x4);
    operator.addUpdateForScheduler(camUpdate2);
    CameraUpdate camUpdate4 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate3.setTakePicture();
    operator.addUpdateForScheduler(camUpdate4);
    CameraUpdate camUpdate5 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate3.setTakePicture();
    operator.addUpdateForScheduler(camUpdate5);







    //start up the test
    Scheduler scheduler = new Scheduler(debrisCollection, operator, camera);
}
}
