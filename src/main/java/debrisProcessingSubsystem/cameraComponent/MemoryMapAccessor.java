package debrisProcessingSubsystem.cameraComponent;

import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.updateSystem.Update;
import sensor.ZoomLevel;

/**
 * This interface is to be implemented by the object that interacts with the
 * memory map.
 * Created by jdt on 3/26/17.
 */
public interface MemoryMapAccessor {
  /**
   * Turn on the camera
   * @return True if successfully turned on.
   */
  public boolean on();

  /**
   * Turn the camera off.
   * @return True if successfully turned off.
   */
  public boolean off();

  /**
   * Reset the camera
   * @return True if reset is successful.
   */
  public boolean reset();

  public void takePicture();

  /**
   * Set the zoom level of the camera
   * @param zoomLevel Level to set
   * @return True if zoom successfully changed.
   */
  public boolean setZoomLevel(ZoomLevel zoomLevel);

  /**
   * Testing method for filling "debris register" with a list of debris objects.
   * @param update
   */
  public void addDebrisToRegister(DebrisRecord update);

  public Update checkMap();
}
