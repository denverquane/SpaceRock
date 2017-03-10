package debrisProcessingSubsystem.debrisCollection;

/**
 * This will be the interface implemented by the Debris Collection object shown
 * in the SADD.
 * This interface should contain all outward facing methods for the
 * Debris collection data structure.
 *
 * This is a preliminary placeholder and very subject to change.
 * Created by jdt on 3/4/17.
 */
public interface DebrisCollection {



    /**
     * This method will be called when it is time to add a new image to the
     * debris list.
     * @return True on success.
     */
    public boolean newImage();

    /**
     * Add a new debris item.
     * TODO this should take some debris parameters.
     */
    public void addDebris();

    /**
     * This method is to be called when. SADD team appears to envision this
     * implemented with an iterator.
     * possibly within the debris list itself?
     */
    public DebrisRecord getDebris();

    /**
     * Print some information about the collection.
     * Testing method.
     */
    public void printCollectionCharacteristics();

    //methods swapList and clearList should be internal per prof roman.
}
