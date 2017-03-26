package debrisProcessingSubsystem.updateSystem;

import debrisProcessingSubsystem.cameraComponent.CameraStatusReport;
import debrisProcessingSubsystem.debrisCollection.DebrisRecord;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * The update specifically for the Operator component which contains information
 * the operator object needs to update.
 * @author Nicholas Spurlok
 *
 */
public class OperatorUpdate extends Update
{
  public enum OperatorUpdateParameters{
    DEBRIS,
    RAW_IMAGE,
    CAMERA_STATUS,
    DEBRIS_TRANSMISSION_COMPLETE,
    CHECK_CONNECTION
  }

  HashMap<OperatorUpdateParameters, Object> paramMap;

  /**
   * Construct new OperatorUpdate.
   * @param updateType UpdateType enum that will allow the Scheduler to know that
   *                   it needs to send this update to the Operator component.
   */
  public OperatorUpdate(UpdateType updateType)
  {
    super(updateType);
    paramMap = new HashMap<>();
  }

  /**
   * Package a debris object to return to operator.
   * @param debris A DebrisRecord object that will be interpreted by the Operator
   *               object.
   */
  public void setDebris(DebrisRecord debris){
    paramMap.put(OperatorUpdateParameters.DEBRIS, debris);
  }

  /**
   * Package a raw image to be returned to the operator.
   * @param img A BufferedImage that will be returned to the operator.
   */
  public void setRawImage(BufferedImage img){
    paramMap.put(OperatorUpdateParameters.RAW_IMAGE, img);
  }

  /**
   * Inform the Operator that there are no more pending debris objects to be
   * transmitted
   */
  public void setDebrisTransmissionComplete(){
    paramMap.put(OperatorUpdateParameters.DEBRIS_TRANSMISSION_COMPLETE, new Boolean(true));
  }

  public void setCheckConnection(){
    paramMap.put(OperatorUpdateParameters.CHECK_CONNECTION, new Boolean(true));
  }

  public void setCameraStatus(CameraStatusReport cameraStatusModel){
    paramMap.put(OperatorUpdateParameters.CAMERA_STATUS, cameraStatusModel);
  }

  public HashMap<OperatorUpdateParameters, Object> getParamMap(){
    return paramMap;
  }

}
