package debrisProcessingSubsystem.cameraComponent;

import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.updateSystem.Update;
import fpga.memory.MemoryMap;
import fpga.memory.NoSuchRegisterFoundException;
import fpga.memory.UnavailbleRegisterException;
import sensor.ZoomLevel;

/**
 * This class creates an object that interacts with the fpga through the memory
 * map.
 * This will be the object that interacts with the MemoryMap in the full
 * implementation of the project.
 * Created by jdt on 3/26/17.
 */
public class FPGAComs implements MemoryMapAccessor{

  /**
   * Turn on the camera.
   * @return True if successfully turns on camera.
   */
  public boolean on(){
    boolean successful = false;
    try {
      MemoryMap.write("turnOnCamera", true);
      successful = true;
    } catch (NoSuchRegisterFoundException e) {
      e.printStackTrace();
    } catch (UnavailbleRegisterException e) {
      e.printStackTrace();
    }
    return successful;
  }

  public boolean off(){
    boolean successful = false;
    try {
      MemoryMap.write("turnOffCamera", true);
      successful = true;
    } catch (NoSuchRegisterFoundException e) {
      e.printStackTrace();
    } catch (UnavailbleRegisterException e) {
      e.printStackTrace();
    }
    return successful;
  }

  /**
   * Tell camera to take picture.
   */
  public void takePicture(){
    try {
      MemoryMap.write("takePicture", true);
    } catch (NoSuchRegisterFoundException e) {
      e.printStackTrace();
    } catch (UnavailbleRegisterException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reset the camera
   * @return currently true. Needs to power cycle the camera and set the zoom to
   * none.
   */
  public boolean reset(){
    return true;
  }

  /**
   * Set the camera's zoom level.
   * @return True if successful.
   */
  public boolean setZoomLevel(ZoomLevel zoomLevel){
    return true;
  }

  public void addDebrisToRegister(DebrisRecord update){
    //debrisRegister.addLast(update);
    System.err.println("Feature not available on FPGA");
  }

  /**
   * Check the memory map for new updates to send back.
   * @return An Update to return.
   */
  public Update checkMap(){
    //check the memory map for updates.
    return null;
  }
}
