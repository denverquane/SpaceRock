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
  /*
  * This is a begin new image update.
  * Set true when about to send a new image.
   */
  public boolean beginNewImage;

  /*
  * Begin transmitting debris data home. This should be set when the scheduler
  * is ready to begin accepting debris for transmission home.
   */
  public boolean sendDebrisHome;

  /*
  * Add a debris object to the new list. If this is true, DebrisCollection will
  * look for an attached debris object
   */
  public boolean addDebris;
  public Debris debrisObject;

  /*
  * This is a raw image request. The DebrisCollection will find and return a
  * raw image using the object id specified in the string imageName.
   */
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




