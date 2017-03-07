package debrisProcessingSubsystem.debrisCollection;

/**
 * This is the interface for the debris list objects under the DebrisCollection
 * object in the SADD.
 * This interface should contain all outward facing methods for the debris lists.
 * Created by jdt on 3/5/17.
 */
public interface DebrisList {
    /**
     * This method will be used to get a debris element from the list.
     */
    public DebrisRecord getDebrisElement();

    /**
     * This method will add a new DebrisRecord to the list.
     * @param newDebrisRecord A new DebrisRecord.
     */
    public void addDebris(DebrisRecord newDebrisRecord);

    public int size();

    /**
     * This method will be used to tell if the list is empty.
     * @return True if empty.
     */
    public boolean isEmpty();
}
