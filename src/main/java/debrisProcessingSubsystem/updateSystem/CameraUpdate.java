package debrisProcessingSubsystem.updateSystem;

import fpga.objectdetection.Debris;

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
  private List<Debris> debris = new ArrayList<>();

  public CameraUpdate(UpdateType updateType)
  {
    super(updateType);
  }

  public void setDebris(List<Debris> debris) {
    this.debris = debris;
  }

  public List<Debris> getDebris() {
    return debris;
  }

}
