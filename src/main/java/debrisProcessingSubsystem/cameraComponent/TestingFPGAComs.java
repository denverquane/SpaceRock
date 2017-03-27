package debrisProcessingSubsystem.cameraComponent;

import debrisProcessingSubsystem.updateSystem.Update;
import sensor.ZoomLevel;

import java.util.LinkedList;

/**
 * This class will simulate the memory map for testing the CPU side components.
 * Created by jdt on 3/26/17.
 */
public class TestingFPGAComs implements MemoryMapAccessor {
  private final String COMPONENT_ID = "MEMORY MAP: ";
  private LinkedList<Update> debrisRegister;

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

  public void addDebrisToRegister(Update update){
    debrisRegister.addLast(update);
  }
}
