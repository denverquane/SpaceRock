package debrisProcessingSubsystem.schedulerTester;

import debrisProcessingSubsystem.debrisCollection.DebrisRecord;
import debrisProcessingSubsystem.updateSystem.Update;

/**
 * This interface will allow access to the component for testing.
 * Created by jdt on 3/26/17.
 */
public interface TestableComponent {
  /**
   * Load an update for this component to send to scheduler. Updates using this
   * should go into the components outgoing update queue.
   * @param update Update to be sent to scheduler.
   */
  public void addUpdateForScheduler(Update update);

  /**
   * This method should load data to be sent back as an update. The intent is to
   * use this to store many debris records for the Camera component to start sending
   * when is being asked to send debris.
   * @param update
   */
  public void addDebrisRecord(DebrisRecord update);
}
