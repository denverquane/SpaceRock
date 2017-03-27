package debrisProcessingSubsystem.updateSystem;

import sensor.ZoomLevel;

import java.util.HashMap;

/**
 * The update specifically for the Camera component which contains information
 * the camera object needs to update.
 * @author Nicholas Spurlok
 *
 */
public class CameraUpdate extends Update
{
  public enum CameraUpdateParameters {
    TURN_ON_CAMERA,
    TURN_OFF_CAMERA,
    RESET_CAMERA,
    TAKE_PICTURE,
    SET_ZOOM,
    PROCESS_IMAGE,
  }

  private HashMap<CameraUpdateParameters, Object> param_map;
  private boolean DEBUG = true;

  public CameraUpdate(UpdateType updateType)
  {
    super(updateType);
    this.param_map = new HashMap<>();
  }


  public void setTurnOnCamera() {
    param_map.clear();
    param_map.put(CameraUpdateParameters.TURN_ON_CAMERA, true);
  }

  public void setTurnOffCamera() {
    param_map.clear();
    param_map.put(CameraUpdateParameters.TURN_OFF_CAMERA, true);
  }

  public void setResetCamera() {
    param_map.clear();
    param_map.put(CameraUpdateParameters.RESET_CAMERA, true);
  }

  public void setTakePicture() {
    param_map.clear();
    param_map.put(CameraUpdateParameters.TAKE_PICTURE, true);
    param_map.remove(CameraUpdateParameters.TURN_ON_CAMERA);
    param_map.remove(CameraUpdateParameters.TURN_OFF_CAMERA);
    param_map.remove(CameraUpdateParameters.RESET_CAMERA);
  }

  public void setZoomLevel(ZoomLevel zoom_level) {
    param_map.put(CameraUpdateParameters.SET_ZOOM, zoom_level);
    param_map.remove(CameraUpdateParameters.TURN_ON_CAMERA);
    param_map.remove(CameraUpdateParameters.TURN_OFF_CAMERA);
    param_map.remove(CameraUpdateParameters.RESET_CAMERA);
  }

  public void setProcessImage() {
    param_map.put(CameraUpdateParameters.PROCESS_IMAGE, true);
    param_map.remove(CameraUpdateParameters.TURN_ON_CAMERA);
    param_map.remove(CameraUpdateParameters.TURN_OFF_CAMERA);
    param_map.remove(CameraUpdateParameters.RESET_CAMERA);
  }

  public HashMap<CameraUpdateParameters, Object> getParamMap() {
    return param_map;
  }

}
