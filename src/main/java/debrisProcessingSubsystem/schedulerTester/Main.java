package debrisProcessingSubsystem.schedulerTester;

import java.awt.image.BufferedImage;

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
    OperatorUpdate stop = new OperatorUpdate(UpdateType.STOP_SCHEDULER);
    
    //Set camera update params
    //camUpdate.setProcessImage(); --This caused memory map null pointer exceptions
    //camUpdate.setTakePicture();  --This causedmemory map null pointer exceptions
    camUpdate.setZoomLevel(ZoomLevel.x4);
    //camUpdate.setTurnOffCamera();  --This causedmemory map null pointer exceptions
    //camUpdate.setTurnOnCamera();  --This causedmemory map null pointer exceptions
    System.out.println("Camera update confermations:");
    sched.sendUpdate(camUpdate);
    System.out.println();
    
    //change debris state with update
    debUpdate.setAddDebris(true);
    debUpdate.setRawImageRequest(true);
    debUpdate.setImageName("Test Name");
    System.out.println("Debris update confermations:");
    sched.sendUpdate(debUpdate);
    System.out.println();
    
    //Print operator state
    opUpdate.setCheckConnection();
    opUpdate.setDebris(new DebrisRecord());
    opUpdate.setDebrisTransmissionComplete();
    opUpdate.setRawImage(new BufferedImage(500, 500, 1));
    System.out.println("Operator update confermations:");
    sched.sendUpdate(opUpdate);

    //Stop the scheduler
    sched.getOperator().updateComponent(stop);
  }
}
