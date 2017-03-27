package debrisProcessingSubsystem.updateSystem;

import java.util.HashMap;

import debrisProcessingSubsystem.updateSystem.Update;
import debrisProcessingSubsystem.updateSystem.UpdateType;
import fpga.objectdetection.Debris;

/**
 * The update specifically for the Debris Collector component which contains information
 * the debris collector object needs to update.
 * 
 * Example Usage:
 * 
 * --Generate update
 * DebrisCollectorUpdate update = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
 * update.setBeginNewImage(true);
 * update.setDebrisObject(new DebrisObject);
 * 
 * --Send update via Scheduler
 * 
 * --Receive update
 * HashMap<DebrisCollectorParameters, Object> updateMap = update.getParamMap;
 * 
 * There would be a function whos job would be to go through each param and check if its in the map. If it is
 * then set the updated value.
 * 
 * Anyone sending an update would be able to see the available params (if using something w/auto-complete then they would get a list
 * of available params to choose from).
 * if (updateMap.get(DebrisCollectorParameters.BEGIN_NEW_IMAGE) != null)
 * {
 *    newImage = (boolean)updateMap.get(DebrisCollectorParameters.BEGIN_NEW_IMAGE);
 * }
 * 
 * if (updateMap.get(DebrisCollectorParameters.DEBRIS_OBJECT) != null)
 * {
 *    debris = (Debris)updateMap.get(DebrisCollectorParameters.DEBRIS_OBJECT);
 * }
 * .
 * .
 * .
 * 
 * 
 * @author Nicholas Spurlok
 *
 */
public class DebrisCollectorUpdate extends Update
{
  public enum DebrisCollectorParameters
  {
    BEGIN_NEW_IMAGE,
    SEND_DEBRIS_HOME,
    ADD_DEBRIS,
    DEBRIS_OBJECT,
    RAW_IMAGE_REQUEST,
    IMAGE_NAME,
    ALL_DEBRIS_SENT
  }
  private HashMap<DebrisCollectorParameters, Object> paramMap;

  public void setBeginNewImage(boolean bool)
  {
    paramMap.put(DebrisCollectorParameters.BEGIN_NEW_IMAGE, bool);

  }
  
  public void setSendDebrisHome(boolean bool)
  {
    paramMap.put(DebrisCollectorParameters.SEND_DEBRIS_HOME, bool);
  }
  
  public void setAddDebris(boolean bool)
  {
    paramMap.put(DebrisCollectorParameters.ADD_DEBRIS, bool);
  }
  
  public void setDebrisObject(Debris debris)
  {
    paramMap.put(DebrisCollectorParameters.DEBRIS_OBJECT, debris);
  }
  
  public void setRawImageRequest(boolean bool)
  {
    paramMap.put(DebrisCollectorParameters.RAW_IMAGE_REQUEST, bool);
  }
  
  public void setImageName(String name)
  {
    paramMap.put(DebrisCollectorParameters.IMAGE_NAME, name);
  }
  
  public HashMap<DebrisCollectorParameters, Object> getParamMap()
  {
    return paramMap;
  }
  public DebrisCollectorUpdate(UpdateType updateType)
  {
    super(updateType);
    paramMap = new HashMap<>();

  }

}