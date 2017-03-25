package debrisProcessingSubsystem.operatorComponent;

import debrisProcessingSubsystem.updateSystem.Update;

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

  private boolean connectionUp;
  public ToGroundLink(){
    connectionUp = true;
  }

  public void sendUpdateToGround(Update update){
    //Do update stuff
  }

  /**
   * Check to confirm a connection to ground is available.
   * @return True if there is a connection to the ground.
   */
  public boolean connectionIsUp(){
    return connectionUp;
  }
}
