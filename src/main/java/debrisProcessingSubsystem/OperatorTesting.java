package debrisProcessingSubsystem;

import debrisProcessingSubsystem.updateSystem.Updatable;
import debrisProcessingSubsystem.updateSystem.Update;

/**
 * This is a testing Operator implementation.
 * Both methods currently return null.
 * Created by jdt on 3/22/17.
 * DSR added Debug code 3/23/17.
 */
public class OperatorTesting implements Updatable {

  private boolean DEBUG = true;
  public Update updateComponent(Update theUpdate){
    if(DEBUG) System.out.println("OperatorTesting received update, returning null");
    return null;
  }
  public Update pollComponent(){
    if(DEBUG) System.out.println("OperatorTesting polled for update, returning null");
    return null;
  }
}
