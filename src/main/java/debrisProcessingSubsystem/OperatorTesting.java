package debrisProcessingSubsystem;

import debrisProcessingSubsystem.updateSystem.Updatable;
import debrisProcessingSubsystem.updateSystem.Update;

/**
 * This is a testing Operator implementation.
 * Both methods currently return null.
 * Created by jdt on 3/22/17.
 */
public class OperatorTesting implements Updatable {

  public Update updateComponent(Update theUpdate){
    return null;
  }
  public Update pollComponent(){
    return null;
  }
}
