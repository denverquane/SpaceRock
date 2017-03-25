package debrisProcessingSubsystem.schedulerTester;

import debrisProcessingSubsystem.Scheduler;
import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.updateSystem.CameraUpdate;
import debrisProcessingSubsystem.updateSystem.DebrisCollectorUpdate;
import debrisProcessingSubsystem.updateSystem.OperatorUpdate;
import debrisProcessingSubsystem.updateSystem.UpdateType;
import sensor.ZoomLevel;


public class Main
{
  public Scheduler scheduler;

  public Main()
  {
    scheduler = new Scheduler();
  }
  public static void main (String args[])
  {
    Main main = new Main();
    Scheduler sched = main.scheduler;
    CameraUpdate camUpdate = new CameraUpdate(UpdateType.CAMERA);
    DebrisCollectorUpdate debUpdate = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
    OperatorUpdate opUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    
    //Print out current camera state
    
    //Set camera update params
    //camUpdate.setProcessImage();
    //camUpdate.setTakePicture();
    camUpdate.setZoomLevel(ZoomLevel.x4);
    sched.sendUpdate(camUpdate);
    
    //Print current debris state
    
    
    //change debris state with update
    debUpdate.setAddDebris(true);
    debUpdate.setRawImageRequest(true);
    debUpdate.setImageName("Test Name");
    sched.sendUpdate(debUpdate);
    
    //Print operator state
    opUpdate.setCheckConnection();
    opUpdate.setDebris(new DebrisRecord());
    sched.sendUpdate(opUpdate);
  }
}
