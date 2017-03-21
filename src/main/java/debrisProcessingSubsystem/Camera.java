package debrisProcessingSubsystem;

import debrisProcessingSubsystem.updateSystem.Updatable;
import debrisProcessingSubsystem.updateSystem.Update;

/**
 * Team 01 will implement the Operator
 * This will be the interface implemented by the Camera object shown
 * in the SADD.
 * This is a preliminary placeholder and very subject to change.
 * Created by dsr on 3/4/17.
 */

public class Camera implements Updatable {

  public Camera() {

  }
  public void on() {

  }

  public void off() {

  }

  public void takePicture() {

  }

  public Update updateComponent(Update theUpdate) {
    return null;
  }

  public Update pollComponent() {
    return null;
  }
}
