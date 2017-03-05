package debrisProcessingSubsystem.debrisCollection;

/**
 * This is the interface for the debris list objects under the DebrisCollection
 * object in the SADD.
 * Created by jdt on 3/5/17.
 */
public interface DebrisList {
    /**
     * This method will be used to get a debris element from the list.
     */
    DebrisRecord getDebrisElement();

    /**
     * This method will be used to tell if the list is empty.
     * @return True if empty.
     */
    boolean isEmpty();
}
