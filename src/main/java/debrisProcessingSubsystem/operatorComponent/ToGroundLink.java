package debrisProcessingSubsystem.operatorComponent;

import debrisProcessingSubsystem.cameraComponent.CameraStatusReport;
import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.updateSystem.Update;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * This class will represent the manager of the link to the ground portion of the
 * operator. The Operator will use this to manage the satellite side of the link
 * between the satellite and ground.
 *
 * Currently, this will provide input and output for demo purposes.
 * Created by jdt on 3/24/17.
 */
public class ToGroundLink {

  private final String COMPONENT_ID = "SATELLITE TRANSMITTER: ";
  private boolean connectionUp;
  public ToGroundLink(){
    connectionUp = true;
  }

  /**
   * Send debris data to earth
   * @param update A debris record object to be translated to ground station format
   *               and sent to earth.
   */
  public void sendDebris(DebrisRecord update){
    System.out.println(COMPONENT_ID + "Sending debris to earth " + update);
  }

  /**
   * Send raw image to earth.
   * @param img A buffered image to be translated to ground station format and
   *            sent to earth.
   */
  public void sendImage(BufferedImage img){
    System.out.println(COMPONENT_ID + "Sending Image to earth " + img);
  }

  /**
   * Notify ground component that image is complete.
   */
  public void notifyImageComplete(){
    System.out.println(COMPONENT_ID + "Notify ground station that image is complete ");
  }

  /**
   * Send camera status report to ground.
   * @param status A camera status report object.
   */
  public void sendCameraStatus(CameraStatusReport status){
    System.out.println(COMPONENT_ID + "Sending Camera Status " + status);
  }

  /**
   * Check to confirm a connection to ground is available.
   * @return True if there is a connection to the ground.
   */
  public boolean connectionIsUp(){
    System.out.println(COMPONENT_ID + "Checking connection status.");
    return connectionUp;
  }

}
