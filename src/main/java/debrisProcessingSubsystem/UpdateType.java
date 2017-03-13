package debrisProcessingSubsystem;

/**
 * Enums used by the scheduler to determine the type of update so it can
 * be passed to the correct component.
 * @author Nicholas Spurlok
 *
 */
public enum UpdateType
{
  CAMERA, OPERATOR, DEBRIS_COLLECTOR, ERROR, DONE, COMMUNICATION_DOWN,
  COMMUNICATION_UP
}
