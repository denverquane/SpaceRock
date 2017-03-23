package debrisProcessingSubsystem.updateSystem;

import fpga.objectdetection.Debris;
import sensor.ZoomLevel;

import java.util.ArrayList;
import java.util.List;

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
    PROCESS_IMAGE,
    RAW_FRAME,
    SET_ZOOM
  }

  public CameraUpdateParameters param;
  public ZoomLevel zoomLevel;
  public int frame_x, frame_y, frame_size;

  public CameraUpdate(UpdateType updateType)
  {
    super(updateType);
  }




}
