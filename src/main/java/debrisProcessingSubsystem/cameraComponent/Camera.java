package debrisProcessingSubsystem.cameraComponent;

import debrisProcessingSubsystem.schedulerTester.TestableComponent;
import debrisProcessingSubsystem.updateSystem.*;
import fpga.memory.EmptyRegisterException;
import fpga.memory.MemoryMap;
import fpga.memory.NoSuchRegisterFoundException;
import fpga.memory.UnavailbleRegisterException;
import fpga.objectdetection.Debris;
import sensor.ZoomLevel;

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

public class Camera implements Updatable, TestableComponent {

  LinkedList<Update> outgoing_updates;
  private boolean DEBUG = true;
  private CameraStatusReport cameraStatusModel;
  private MemoryMapAccessor memoryMap;
  public Camera() {
    cameraStatusModel = new CameraStatusReport();
    //TODO plug in fake memory map.
    memoryMap = new FPGAComs();
    this.outgoing_updates = new LinkedList<>();
  }

  /**
   * Turn on the camera.
   */
  private void on() {
    if(DEBUG){
      System.out.println("Turn on camera.");
    }
    if(memoryMap.on()){
      cameraStatusModel.setIsOn(true);
    }
    OperatorUpdate outgoingUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    outgoingUpdate.setCameraStatus(new CameraStatusReport(cameraStatusModel));
    outgoing_updates.add(outgoingUpdate);
  }

  /**
   * Turn the camera off.
   */
  private void off() {
    if(DEBUG){
      System.out.println("Turn camera off.");
    }
    if(memoryMap.on()){
      cameraStatusModel.setIsOn(false);
    }
    OperatorUpdate outgoingUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    outgoingUpdate.setCameraStatus(new CameraStatusReport(cameraStatusModel));
    outgoing_updates.add(outgoingUpdate);
  }

  /**
   * Reset the camera.
   * TODO this needs to power off, power on, and reset zoom level.
   */
  private void reset() {
    if(DEBUG){
      System.out.println("Reset Camera.");
    }
    OperatorUpdate outgoingUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    cameraStatusModel = new CameraStatusReport();
    outgoingUpdate.setCameraStatus(cameraStatusModel);
    outgoing_updates.add(outgoingUpdate);
  }

  /**
   * Tell camera to take a picture.
   */
  private void takePicture() {
    memoryMap.takePicture();
  }

  /* Raw frame will be returned with every debris object.
  // Sean Hanely
  private void getRawFrame() {
    outgoing_updates.add(new OperatorUpdate(UpdateType.OPERATOR));
  }
  */

  // Corey
  private void setZoomLevel(ZoomLevel zoomLevel) {
    if(DEBUG){
      System.out.println("Setting zoom level to: " + zoomLevel);
    }
    if(memoryMap.setZoomLevel(zoomLevel)){
      cameraStatusModel.setZoomLevel(zoomLevel);
    }
    OperatorUpdate outgoingUpdate = new OperatorUpdate(UpdateType.OPERATOR);
    outgoingUpdate.setCameraStatus(cameraStatusModel);
  }

  // Divya
  /* Camera should call this internally when the image is finished.
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
  }*/


  public Update updateComponent(Update theUpdate) {
    CameraUpdate camera_update = (CameraUpdate)theUpdate;
    camera_update.getParamMap().forEach((param,value) -> {
      switch(param) {
        case TURN_ON_CAMERA:
          on();
          if (DEBUG) System.out.println("Received TURN_ON_CAMERA update.");
          break;
        case TURN_OFF_CAMERA:
          off();
          if (DEBUG) System.out.println("Received TURN_OFF_CAMERA update.");
          break;
        case RESET_CAMERA:
          reset();
          if (DEBUG) System.out.println("Received RESET_CAMERA update.");
          break;
        case TAKE_PICTURE:
          takePicture();
          if (DEBUG) System.out.println("Received TAKE_PICTURE update.");
          break;
        case SET_ZOOM:
          setZoomLevel((ZoomLevel)value);
          if (DEBUG) System.out.println("Received SET_ZOOM update.");
          break;
        case PROCESS_IMAGE:
          //process_image();
          if (DEBUG) System.out.println("Received PROCESS_IMAGE update.");
          break;
        default:
          throw new RuntimeException("I don't understand what you want me to do.");
      }

    });
    //TODO this gets rid of the squiggles, should return something else.
    return null;
  }

  public Update pollComponent() {
    if (outgoing_updates.isEmpty()) {
      return null;
    } else {
      return outgoing_updates.removeFirst();
    }
  }

  public void addUpdateForScheduler(Update update){
    outgoing_updates.addLast(update);
  }

  public void addUpdateAsData(Update update){
    memoryMap.addDebrisToRegister(update);
  }

}
