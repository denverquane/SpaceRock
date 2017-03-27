package debrisProcessingSubsystem.cameraComponent;

import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.updateSystem.DebrisCollectorUpdate;
import debrisProcessingSubsystem.updateSystem.Update;
import debrisProcessingSubsystem.updateSystem.UpdateType;
import sensor.ZoomLevel;

import java.util.LinkedList;

/**
 * This class will simulate the memory map for testing the CPU side components.
 * Created by jdt on 3/26/17.
 */
public class TestingFPGAComs implements MemoryMapAccessor {
  private final String COMPONENT_ID = "MEMORY MAP: ";
  private LinkedList<DebrisRecord> debrisRegister;

  public TestingFPGAComs(){
    debrisRegister = new LinkedList<>();
  }
  public boolean on(){
    System.out.println(COMPONENT_ID + "Turn camera on.");
    return true;
  }

  public boolean off(){
    System.out.println(COMPONENT_ID + "Turn camera off.");
    return true;
  }

  public boolean reset(){
    System.out.println(COMPONENT_ID + "Reset Camera.");
    return true;
  }

  public void takePicture(){
    System.out.println(COMPONENT_ID + "Take picture command.");
  }

  public boolean setZoomLevel(ZoomLevel zoomLevel){
    System.out.println(COMPONENT_ID + "Zoom level set to: " + zoomLevel);
    return true;
  }

  public void addDebrisToRegister(DebrisRecord update){
    debrisRegister.addLast(update);
  }

  public Update checkMap(){
    if(!debrisRegister.isEmpty()){
      DebrisCollectorUpdate retUpdate = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
      retUpdate.setDebrisObject(debrisRegister.removeFirst());
      retUpdate.setAddDebris(true);
      return retUpdate;
    }
    else{
      return null;
    }
  }
}
