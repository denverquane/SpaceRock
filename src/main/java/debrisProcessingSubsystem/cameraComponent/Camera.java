package debrisProcessingSubsystem.cameraComponent;

import debrisProcessingSubsystem.updateSystem.*;
import fpga.memory.EmptyRegisterException;
import fpga.memory.MemoryMap;
import fpga.memory.NoSuchRegisterFoundException;
import fpga.memory.UnavailbleRegisterException;
import fpga.objectdetection.Debris;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Team 01 will implement the Operator
 * This will be the interface implemented by the Camera object shown
 * in the SADD.
 * This is a preliminary placeholder and very subject to change.
 * Created by dsr on 3/4/17.
 */

public class Camera implements Updatable {

  LinkedList<Update> outgoing_updates;

  public Camera() {
    this.outgoing_updates = new LinkedList<>();
  }

 // Jalen
  private void on() {
    try {
      MemoryMap.write("turnOnCamera", true);
    } catch (NoSuchRegisterFoundException e) {
      e.printStackTrace();
    } catch (UnavailbleRegisterException e) {
      e.printStackTrace();
    }
    outgoing_updates.add(new OperatorUpdate(UpdateType.OPERATOR));
  }

  // Jalen
  private void off() {
    try {
      MemoryMap.write("turnOffCamera", true);
    } catch (NoSuchRegisterFoundException e) {
      e.printStackTrace();
    } catch (UnavailbleRegisterException e) {
      e.printStackTrace();
    }
    outgoing_updates.add(new OperatorUpdate(UpdateType.OPERATOR));
  }

  // Corey
  private void reset() {
    outgoing_updates.add(new OperatorUpdate(UpdateType.OPERATOR));
  }

  // Daniel
  private void takePicture() {
    try {
      MemoryMap.write("takePicture", true);
    } catch (NoSuchRegisterFoundException e) {
      e.printStackTrace();
    } catch (UnavailbleRegisterException e) {
      e.printStackTrace();
    }
    outgoing_updates.add(new OperatorUpdate(UpdateType.OPERATOR));
  }

  // Sean Hanely
  private void getRawFrame() {
    outgoing_updates.add(new OperatorUpdate(UpdateType.OPERATOR));
  }

  // Corey
  private void setZoomLevel() {
    outgoing_updates.add(new OperatorUpdate(UpdateType.OPERATOR));
  }

  // Divya
  private void process_image() {
    try {
      Debris debris = MemoryMap.read(Debris.class, "debris");
      if (debris != null) {
        DebrisCollectorUpdate debris_update = new DebrisCollectorUpdate(UpdateType.DEBRIS_COLLECTOR);
        debris_update.setAddDebris(true);
        debris_update.setDebrisObject(debris);
        outgoing_updates.add(debris_update);
        MemoryMap.write("debris", null);
      } else {
        //return new CameraUpdate(UpdateType.DONE);
        // TODO request next frame
      }
    } catch (NoSuchRegisterFoundException e) {
      e.printStackTrace();
    } catch (EmptyRegisterException e) {
      e.printStackTrace();
    } catch (UnavailbleRegisterException e) {
      e.printStackTrace();
    }
  }


  public Update updateComponent(Update theUpdate) {
    CameraUpdate camera_update = (CameraUpdate)theUpdate;
    camera_update.getParamMap().forEach((param,value) -> {
      switch(param) {
        case TURN_ON_CAMERA:
          on();
          break;
        case TURN_OFF_CAMERA:
          off();
          break;
        case RESET_CAMERA:
          reset();
          break;
        case TAKE_PICTURE:
          takePicture();
          break;
        case SET_ZOOM:
          setZoomLevel();
          break;
        case PROCESS_IMAGE:
          process_image();
          break;
        case RAW_FRAME:
          getRawFrame();
          break;
        default:
          throw new RuntimeException("I don't understand what you want me to do.");
      }

    });
  }

  public Update pollComponent() {
    if (outgoing_updates.isEmpty()) {
      return null;
    } else {
      return outgoing_updates.removeFirst();
    }
  }

}
