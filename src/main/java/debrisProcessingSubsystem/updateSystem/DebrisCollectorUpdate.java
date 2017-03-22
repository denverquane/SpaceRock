package debrisProcessingSubsystem.updateSystem;

import debrisProcessingSubsystem.updateSystem.Update;
import debrisProcessingSubsystem.updateSystem.UpdateType;
import fpga.objectdetection.Debris;

/**
 * The update specifically for the Debris Collector component which contains information
 * the debris collector object needs to update.
 * @author Nicholas Spurlok
 *
 */
public class DebrisCollectorUpdate extends Update
{
  public boolean sendDebrisHome;

  public boolean addDebris;
  public Debris debrisObject;

  public boolean rawImageRequest;
  public String imageName;

  public DebrisCollectorUpdate(UpdateType updateType)
  {
    super(updateType);
    sendDebrisHome = false;
    addDebris = false;
    //debrisObject = null;
    rawImageRequest = false;
    imageName = null;
  }

}




