package debrisProcessingSubsystem;

/**
 * Created by scnaegl on 3/9/17.
 */
public enum State {
  WAITING,      // Camera is on and ready awaiting a command
  PROCESSING,   // Camera is getting the next frame or image, output should be expectd soon
  TAKING_IMAGE, // Camera is taking an image
  OFF           // Camera is off or broken
}
