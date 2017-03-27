package debrisProcessingSubsystem.schedulerTester;

import debrisProcessingSubsystem.Scheduler;
import debrisProcessingSubsystem.cameraComponent.Camera;
import debrisProcessingSubsystem.debrisCollection.DebrisCollection;
import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.operatorComponent.OperatorTesting;
import debrisProcessingSubsystem.updateSystem.CameraUpdate;
import debrisProcessingSubsystem.updateSystem.UpdateType;


/**
 * Created by jdt on 3/27/17.
 */
public class TestingJ {
  public static TestableComponent camera, operator, collection;
  public static Scheduler sked;

  public TestingJ(){
    camera = new Camera();
    operator = new OperatorTesting();
    collection = new DebrisCollection();
    //CameraUpdate testUpdate = new CameraUpdate(UpdateType.CAMERA);
    //testUpdate.setTakePicture();
    //operator.addUpdateForScheduler(testUpdate);
    camera.addDebrisRecord(new DebrisRecord());
    camera.addDebrisRecord(new DebrisRecord());
    camera.addDebrisRecord(new DebrisRecord());


    sked = new Scheduler((DebrisCollection)collection, (OperatorTesting)operator, (Camera)camera);
  }

  public static void main(String[] args){
    TestingJ tester = new TestingJ();

  }
}
