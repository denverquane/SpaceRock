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

    //set up the first stage of the test
    stageOne();

    //start up the test
    Scheduler scheduler = new Scheduler(debrisCollection, operator, camera);
    stageTwo();
    OperatorUpdate stop = new OperatorUpdate(UpdateType.STOP_SCHEDULER);
    operator.addUpdateForScheduler(stop);

  }
  public static void stageOne()
  {
    //Operator commands to the camera
    //CameraUpdate camUpdate = new CameraUpdate(UpdateType.CAMERA);
    //camUpdate.setTurnOnCamera();
    //operator.addUpdateForScheduler(camUpdate);
    CameraUpdate camUpdate2 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate2.setResetCamera();
    operator.addUpdateForScheduler(camUpdate2);
    CameraUpdate camUpdate3 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate3.setZoomLevel(ZoomLevel.x4);
    operator.addUpdateForScheduler(camUpdate3);
    CameraUpdate camUpdate4 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate4.setTakePicture();
    operator.addUpdateForScheduler(camUpdate4);
    CameraUpdate camUpdate5 = new CameraUpdate(UpdateType.CAMERA);
    camUpdate5.setTakePicture();
    operator.addUpdateForScheduler(camUpdate5);
  }
  public static void stageTwo()
  {
    //camera updates
    //Camera updates to debrisCollector
    DebrisCollectorUpdate dbu1 = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
    dbu1.setBeginNewImage(true);
    camera.addUpdateForScheduler(dbu1);
    DebrisCollectorUpdate dbu2 = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
    dbu2.setAddDebris(true);
    camera.addUpdateForScheduler(dbu2);
    // DebrisCollectorUpdate dbu3 = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
    //todo need debris object to send
    //dbu3.setDebrisObject();
    //camera.addUpdateForScheduler(dbu3);
    

  }
}
